package com.example.demo.service;

import com.example.demo.model.LoginResponseDTO;
import com.example.demo.model.ReqLogin;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Service
public class LoginServiceImpl implements LoginService {

    @Value("${tp.datasource.url}")
    private String jdbcUrl;
    @Value("${tp.datasource.username}")
    private String dbUsername;
    @Value("${tp.datasource.password}")
    private String dbPassword;

    @Override
    public LoginResponseDTO login(ReqLogin reqLogin) {

        String jdbcUrl = "jdbc:mysql://localhost:3306/rrhh_db";
        String dbUsername = "root";
        String dbPassword = "root";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            Statement statement = connection.createStatement();

            // Vulnerabilidad de SQL Injection al concatenar directamente el input en la consulta
            String query = "SELECT * FROM users WHERE dni = '" + reqLogin.getDni() + " ' AND password = '" + reqLogin.getPassword() + "'";
            ResultSet resultSet = statement.executeQuery(query);

            StringBuilder user = new StringBuilder();
            int count = 0;

            User usr = new User();
            while (resultSet.next()) {
                // Suponiendo que quieres ver los campos "dni" y "password" de cada usuario
                usr.setDni(resultSet.getString("dni"));
                usr.setName(resultSet.getString("name"));
                usr.setUser(resultSet.getString("user"));
                usr.setEmail(resultSet.getString("email"));
                user.append("DNI: ").append(usr.getDni())
                        .append(", Password: ").append(resultSet.getString("password"))
                        .append("<br>");
                count += 1;
            }
            if (count == 1) {
                return getLoginResponseDTO(usr);
            } else {
                return getLoginResponseDTO(user.toString());
            }

        } catch (Exception e) {
            return getLoginResponseDTO(e.getMessage());
        }
    }

    private static LoginResponseDTO getLoginResponseDTO(User usr) throws SQLException {

        LoginResponseDTO loginResponseDTO= new LoginResponseDTO();
        loginResponseDTO.setUser(usr);
        loginResponseDTO.setErrorMessage("");
        loginResponseDTO.setRedirectUrl("/home");
        return loginResponseDTO;
    }

    private static LoginResponseDTO getLoginResponseDTO(String e) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setErrorMessage(e);
        loginResponseDTO.setRedirectUrl("/login");
        return loginResponseDTO;
    }

}
