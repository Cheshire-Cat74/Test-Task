package com.example.testtask.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserShortDto {

    String login;
    String fullName;
    String phoneNumber;
    String email;
    String birthDay;
}
