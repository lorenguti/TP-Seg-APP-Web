package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ServicesController {

    @GetMapping("/updateProfile")
    public String updateProfile() {
        // Aquí iría la lógica de actualización de perfil
        return "update_profile";
    }

    @GetMapping("/payroll")
    public String payroll() {
        // Aquí iría la lógica de consulta de nóminas
        return "recibos";
    }

}