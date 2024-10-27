package com.example.demo.service;

import org.springframework.web.bind.annotation.RequestParam;

public interface LoginService {
    String login(@RequestParam String dni, @RequestParam String password);
}
