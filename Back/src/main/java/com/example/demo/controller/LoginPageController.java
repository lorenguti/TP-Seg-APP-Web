package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";  // Esto corresponde al archivo `src/main/resources/templates/login.html`
    }
}
