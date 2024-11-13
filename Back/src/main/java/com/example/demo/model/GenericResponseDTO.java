package com.example.demo.model;

import lombok.Data;

@Data
public class GenericResponseDTO {
    private String errorMessage;
    private String redirectUrl;
}
