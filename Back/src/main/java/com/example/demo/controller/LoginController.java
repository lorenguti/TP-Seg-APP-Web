package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestParam String dni, @RequestParam String password) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/rrhh_db"; // Reemplaza con tu base de datos
        String dbUsername = "root"; // Usuario de MySQL
        String dbPassword = "Elpeque7/85"; // Contraseña de MySQL

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            Statement statement = connection.createStatement();

            // Vulnerabilidad de SQL Injection al concatenar directamente el input en la consulta
            String query = "SELECT * FROM users WHERE dni = '" + dni + "' AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return "Login exitoso: Bienvenido, " + resultSet.getString("email");
            } else {
                return "Credenciales incorrectas";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error en el login";
        }
    }
}
