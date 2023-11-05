package com.example.testtask;

import com.example.testtask.dto.UserDto;
import com.example.testtask.dto.UserShortDto;
import com.example.testtask.entity.DepartmentEntity;
import com.example.testtask.entity.UserEntity;
import com.example.testtask.entity.UserRoleEntity;
import com.example.testtask.feign.ExternalServiceFeign;
import com.example.testtask.mapper.UserMapper;
import com.example.testtask.mapper.UserMapperImpl;
import com.example.testtask.repository.DepartmentRepository;
import com.example.testtask.repository.UserRepository;
import com.example.testtask.service.UserServiceImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ExternalServiceFeign externalServiceFeign;
    @Mock
    private UserMapper userMapper;
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName(" обновление пользователей по тех. идентификатору департамента и датой последнего изменения меньше 30 дней ")
    void updateByDepartmentAndLastUpdateDateLessThirtyDay() {

        UserEntity expectedUser = supplyUser();
        UserShortDto expectedShortUser = supplyShortUser();

        when(userRepository.findAllByDepartmentAndUpdateDate(
                any(DepartmentEntity.class), any(LocalDateTime.class))
        ).thenReturn(List.of(expectedUser));

        when(departmentRepository.findById(anyLong())).thenReturn(
                Optional.of(supplyDepartment())
        );

        when(externalServiceFeign.readUserByLogin(anyString())).thenReturn(expectedShortUser);

        UserMapperImpl mapper = new UserMapperImpl();
        UserEntity mergedUser = mapper.mergeToEntity(expectedUser, expectedShortUser);
        UserDto mappedUser = mapper.toDto(mergedUser);

        when(userMapper.mergeToEntity(any(UserEntity.class), any(UserShortDto.class))).thenReturn(mergedUser);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(mappedUser);
        when(userRepository.save(any(UserEntity.class))).thenReturn(mergedUser);

        List<UserDto> actualList = userService.updateByDepartmentAndLastUpdateDateLessThirtyDay(1L);
        UserDto actual = actualList.get(0);

        assertAll(
                () -> {
                    assertEquals(1, actualList.size());

                    assertEquals(expectedShortUser.getEmail(), actual.getEmail());
                    assertEquals(expectedShortUser.getLogin(), actual.getLogin());
                    assertEquals(expectedShortUser.getFullName(), actual.getFullName());
                    assertEquals(expectedShortUser.getPhoneNumber(), actual.getPhoneNumber());

                    assertEquals(expectedUser.getActive(), actual.getActive());
                    assertEquals(expectedUser.getRole().getName(), actual.getRole());
                    assertEquals(expectedUser.getUpdateDate(), actual.getUpdateDate());
                    assertEquals(expectedUser.getDepartment().getId(), actual.getDepartmentId());
                }
        );
    }

    private UserShortDto supplyShortUser() {

        return new UserShortDto(
                "newLogin", "Свиридов Иван Иванович",
                "89998887778", "newEmail.gmail.com", "1998-02-03"
        );
    }

    private DepartmentEntity supplyDepartment() {

        return new DepartmentEntity(1L, "testDepartment", Boolean.TRUE);
    }

    private UserEntity supplyUser() {

        UserRoleEntity userRole = new UserRoleEntity(1L, "ADMIN");

        DepartmentEntity department = supplyDepartment();

        return new UserEntity(
                1L, "testLogin", "testPassword",
                "Иванов Иван Иванович", userRole, "test@gmail.com", department,
                LocalDate.of(1997, Month.APRIL, 19), Boolean.TRUE, null, LocalDateTime.now()
        );
    }
}