package com.example.testtask.controller;

import com.example.testtask.dto.UserDto;
import com.example.testtask.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/users/update")
    public List<UserDto> updateCertainUser(@RequestParam("departmentId") Long departmentId) {

        return userService.updateByDepartmentAndLastUpdateDateLessThirtyDay(departmentId);
    }

    /**
     * @param pageNumber номер страницы(параметр обязателен, если не задать получим 400 статус и если задать null)
     * @param pageSize размер страницы(параметр обязателен, если не задать получим 400 статус и если задать null)
     * @return список ДТО пользователей, которые соответствуют требованиям, а именно активны, имеют роль 'ADMIN',
     *      * входят в департамент, в котором больше 10 пользователей
     */
    @GetMapping("/users")
    public List<UserDto> readAllCertainUsers(
            @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {

        return userService.getAllCertainUsers(pageNumber, pageSize);
    }
}
