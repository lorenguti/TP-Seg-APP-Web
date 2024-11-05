package com.example.demo.controller.rest;

import com.example.demo.model.LoginResponseDTO;
import com.example.demo.model.ReqLogin;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private LoginService loginService;
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService=loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> handleLogin(@RequestBody ReqLogin reqLogin) {
        LoginResponseDTO loginResponseDTO = loginService.login(reqLogin);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
