package com.example.demo.controller.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // Renderiza la plantilla login.html
    }

    @PostMapping("/login")
    public String handleLogin( @RequestParam("dni") String dni, @RequestParam("password") String password, Model model) {
        
        String jdbcUrl = "jdbc:mysql://localhost:3306/rrhh_db"; // Reemplaza con tu base de datos
        String dbUsername = "root"; // Usuario de MySQL
        String dbPassword = "Elpeque7/85"; // Contraseña de MySQL

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            Statement statement = connection.createStatement();

            // Vulnerabilidad de SQL Injection al concatenar directamente el input en la consulta
            String query = "SELECT * FROM users WHERE dni = '" + dni + "' AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(query);

            
            if (resultSet.next()) {
                return "redirect:/home";  // Redirige a la página de inicio
            } else {
                model.addAttribute("error", "Usuario o contraseña incorrectos");
                return "login";  // Muestra el login nuevamente con un mensaje de error
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error en el login";
        }
    }
}
