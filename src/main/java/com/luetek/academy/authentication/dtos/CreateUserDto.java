package com.luetek.academy.authentication.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserDto {
    private String username;
    private String password;
    private String confirmPassword; // For re-enter password
    private String email;
    private String fullName;
}
