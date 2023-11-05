package com.example.testtask.mapper;

import com.example.testtask.dto.UserDto;
import com.example.testtask.dto.UserShortDto;
import com.example.testtask.entity.UserEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "login", source = "externalShortUser.login")
    @Mapping(target = "fullName", source = "externalShortUser.fullName")
    @Mapping(target = "email", source = "externalShortUser.email")
    @Mapping(target = "birthDay", source = "externalShortUser.birthDay")
    @Mapping(target = "phoneNumber", source = "externalShortUser.phoneNumber")
    UserEntity mergeToEntity(UserEntity user, UserShortDto externalShortUser);

    @Mapping(target = "role", source = "user.role.name")
    @Mapping(target = "departmentId", source = "user.department.id")
    UserDto toDto(UserEntity user);

    List<UserDto> toDtoList(List<UserEntity> users);
}
