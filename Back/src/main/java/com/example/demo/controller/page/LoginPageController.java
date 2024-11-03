package com.example.demo.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

 @Controller
public class LoginPageController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // Renderiza la plantilla login.html
    }

    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }
}
