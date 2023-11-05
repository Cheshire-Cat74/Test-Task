package com.example.testtask.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    String login;
    String fullName;
    String phoneNumber;
    String email;
    String role;
    Long departmentId;
    LocalDate birthDay;
    Boolean active;
    LocalDateTime updateDate;
}
