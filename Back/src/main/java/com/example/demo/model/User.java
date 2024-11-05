package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String name;
    private String user;
    private String email;
    private String dni;

    public User() {

    }

}
