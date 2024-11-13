package com.example.demo.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class ReqLogin {
    private String dni;
    private String password;
}
