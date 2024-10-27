package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class LoginServiceImpl implements LoginService {

    @Value("${tp.datasource.url}")
    private String jdbcUrl;
    @Value("${tp.datasource.username}")
    private String dbUsername;
    @Value("${tp.datasource.password}")
    private String dbPassword;

    @Override
    public String login(@RequestParam String dni, @RequestParam String password) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            // Usar PreparedStatement para evitar inyección SQL
            String query = "SELECT * FROM users WHERE dni = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

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
