package com.example.demo.model;


import lombok.Data;

@Data
public class LoginResponseDTO extends GenericResponseDTO {
    private User user;
}
