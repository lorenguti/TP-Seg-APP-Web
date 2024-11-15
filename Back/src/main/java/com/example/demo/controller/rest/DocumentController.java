package com.example.demo.controller.rest;

import com.example.demo.model.Document;
import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Base64;

import java.util.List;

@Controller
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // Endpoint para mostrar la lista de documentos
    @GetMapping("/files")
    public String listDocuments(Model model) {
        List<Document> documents = documentService.getDocumentsForUser();
        model.addAttribute("documents", documents);
        return "files";
    }

    @GetMapping("/files/receipts/{id}")
    public ResponseEntity<Resource> getReceipt(@PathVariable Long id) {
        Resource file = documentService.getReceiptById(id);
        byte[] encodedBytes = Base64.getEncoder().encode(file.getFilename().getBytes());
        System.out.println("encodedBytes " + new String(encodedBytes));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    // Endpoint para descargar un documento
    @GetMapping("/files/contracts/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {       
        Resource file = documentService.getDocumentById(id);
        byte[] encodedBytes = Base64.getEncoder().encode(file.getFilename().getBytes());
        System.out.println("encodedBytes " + new String(encodedBytes));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(encodedBytes) + "\"")
                .body(file);
    }   

}
