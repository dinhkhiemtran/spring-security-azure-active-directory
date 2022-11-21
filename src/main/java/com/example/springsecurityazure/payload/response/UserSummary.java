package com.example.springsecurityazure.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserSummary {
    private Long id;
    private String name;
}
