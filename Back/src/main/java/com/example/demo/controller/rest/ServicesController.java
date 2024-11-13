package com.example.demo.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ServicesController {

    @GetMapping("/updateProfile")
    public String updateProfile() {
        return "update_profile";
    }

    @GetMapping("/payroll")
    public String payroll() {
        return "recibos";
    }

}
