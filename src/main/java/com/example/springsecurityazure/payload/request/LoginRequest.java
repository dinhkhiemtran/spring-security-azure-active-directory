package com.example.springsecurityazure.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String nameOrEmail;

    @NotBlank
    private String password;

}
