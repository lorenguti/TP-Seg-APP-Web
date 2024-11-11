package com.example.demo.controller.page;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.ReqRegister;


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

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@RequestBody ReqRegister reqLogin) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/rrhh_db";
        String dbUsername = "root";
        String dbPassword = "Elpeque7/85";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            Statement statement = connection.createStatement();
            String query = "UPDATE `rrhh_db`.`users` SET `password` = '" + reqLogin.getPassword() + "', `registered` = '1' WHERE (`dni` = " + reqLogin.getDni() + ")";
            statement.executeUpdate(query);
            return "redirect:/login";
           
        } catch (Exception e) {
            return "error";
        }
    }
}
