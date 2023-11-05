package com.example.testtask.service;

import com.example.testtask.dto.UserDto;
import java.util.List;

public interface UserService {

    List<UserDto> updateByDepartmentAndLastUpdateDateLessThirtyDay(Long departmentId);

    List<UserDto> getAllCertainUsers(Integer pageNumber, Integer size);
}
