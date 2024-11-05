package com.example.demo.service;

import com.example.demo.model.LoginResponseDTO;
import com.example.demo.model.ReqLogin;
import org.springframework.web.bind.annotation.RequestParam;

public interface LoginService {
    LoginResponseDTO login(ReqLogin reqLogin);
}
