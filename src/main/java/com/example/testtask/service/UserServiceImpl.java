package com.example.testtask.service;

import com.example.testtask.dto.UserDto;
import com.example.testtask.dto.UserShortDto;
import com.example.testtask.entity.DepartmentEntity;
import com.example.testtask.entity.UserEntity;
import com.example.testtask.entity.UserRoleEntity;
import com.example.testtask.feign.ExternalServiceFeign;
import com.example.testtask.mapper.UserMapper;
import com.example.testtask.repository.DepartmentRepository;
import com.example.testtask.repository.UserRepository;
import com.example.testtask.repository.UserRoleRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final DepartmentRepository departmentRepository;
    private final ExternalServiceFeign externalServiceFeign;

    /**
     * @param departmentId технический идентификатор департамента
     * @return список с ДТО обновленных пользователей
     *
     * EntityNotFoundException не отлавливается через RestControllerAdvice, так как нет в ТЗ
     */
    @Override
    @Transactional
    public List<UserDto> updateByDepartmentAndLastUpdateDateLessThirtyDay(Long departmentId) {

        DepartmentEntity department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("по departmentId:%d в таблице department ничего не найдено", departmentId))
                );

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earlierForThirtyDay = now.minus(30, ChronoUnit.DAYS);

        List<UserEntity> users = userRepository.findAllByDepartmentAndUpdateDate(
                department, earlierForThirtyDay
        );

        List<UserDto> usersResponse = new ArrayList<>();

        users.forEach(userEntity -> {
            UserShortDto externalShortUser = externalServiceFeign.readUserByLogin(userEntity.getLogin());
            UserEntity unsavedUser = userMapper.mergeToEntity(userEntity, externalShortUser);
            UserEntity user = userRepository.save(unsavedUser);
            usersResponse.add(userMapper.toDto(user));
        });

        return usersResponse;
    }

    /**
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @return список ДТО пользователей, которые соответствуют требованиям, а именно активны, имеют роль 'ADMIN', входят
     * в департамент, в котором больше 10 пользователей с требованиями, как описаны выше
     *
     * EntityNotFoundException не отлавливается через RestControllerAdvice, так как нет в ТЗ так же -1 и любое
     * отрицательное число вызывает 500 в контроллере и не обрабатывается, так как нет в ТЗ
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllCertainUsers(Integer pageNumber, Integer pageSize) {

        String expectedRoleName = "ADMIN";

        UserRoleEntity userRole = userRoleRepository.findByName(expectedRoleName)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("По name:%s в таблице user_role ничего не найдено", expectedRoleName))
                );

        List<UserEntity> users = userRepository.findAllCertainUsers(
                userRole, true, PageRequest.of(pageNumber, pageSize)
        );

        return userMapper.toDtoList(users);
    }
}
