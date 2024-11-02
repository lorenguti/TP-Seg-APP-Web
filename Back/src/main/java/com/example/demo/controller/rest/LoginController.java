package com.example.demo.controller.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // Renderiza la plantilla login.html
    }

    @GetMapping("/")
    public String raiz() {
        return "redirect:/login"; 
    }

    @PostMapping("/login")
    public String handleLogin( @RequestParam("dni") String dni, @RequestParam("password") String password, Model model) {
        
        String jdbcUrl = "jdbc:mysql://localhost:3306/rrhh_db"; // Reemplaza con tu base de datos
        String dbUsername = "root"; // Usuario de MySQL
        String dbPassword = "root"; // Contraseña de MySQL

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            Statement statement = connection.createStatement();

            // Vulnerabilidad de SQL Injection al concatenar directamente el input en la consulta
            String query = "SELECT * FROM users WHERE dni = '" + dni + " ' AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(query);

            StringBuilder user = new StringBuilder();
            int count = 0;


            while (resultSet.next()) { 
                // Suponiendo que quieres ver los campos "dni" y "password" de cada usuario
                user.append("DNI: ").append(resultSet.getString("dni"))
                    .append(", Password: ").append(resultSet.getString("password"))
                    .append("<br>");
                count += 1;
            }

            if (count == 1) {
                model.addAttribute("user", user.toString()); 
                return "redirect:/home";  
            } else {
                model.addAttribute("errorMessage", user.toString());
                return "login"; 
            }

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
    }
}
