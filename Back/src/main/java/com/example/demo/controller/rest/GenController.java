package com.example.demo.controller.rest;

import com.example.demo.model.Document;
import com.example.demo.service.DocumentService;
import com.example.demo.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.List;

@RestController
public class GenController {

    @Autowired
    private EncryptionService encryptionService;

    // Endpoint para mostrar la lista de documentos
    @GetMapping("/genpdf")
    public String genPdf() {
        return encryptionService.exec();
    }

}
