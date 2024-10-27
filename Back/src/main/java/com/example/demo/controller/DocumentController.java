package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    // Endpoint para descargar un documento
    @GetMapping("/files/transactions/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {       
        Resource file = documentService.getDocumentById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
