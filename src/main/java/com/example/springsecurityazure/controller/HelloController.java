package com.example.springsecurityazure.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("admin")
    @ResponseBody
    @PreAuthorize("hasAuthority('APPROLE_Admin')")
    public String Admin() {
        return "Admin message";
    }

    @GetMapping("user")
    @ResponseBody
    @PreAuthorize("hasAuthority('APPROLE_User')")
    public String User() {
        return "User message";
    }

}
