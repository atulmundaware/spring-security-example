package com.atulm.security.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class Welcome {
    @GetMapping
    public String welcomeUser() {
        return "Welcome to spring application demo please use secure apis";
    }
}
