package com.example.demo.service;

import com.example.demo.model.Document;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {

    // Lista estática de documentos como ejemplo
    public List<Document> getDocumentsForUser() {
        List<Document> documents = new ArrayList<>();
        documents.add(new Document(1L, "Contrato de Trabajo", LocalDate.of(2022, 5, 1)));
        documents.add(new Document(2L, "Certificado de Empleo", LocalDate.of(2023, 1, 15)));
        documents.add(new Document(3L, "Declaracion Jurada", LocalDate.of(2023, 9, 30)));
        return documents;
    }

    public Resource getReceiptById(Long id) {
        try {
            String filePath = "/documents/receipts/" + id + ".pdf";
            Path file = Paths.get(getClass().getResource(filePath).toURI());
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se puede leer el archivo");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el archivo", e);
        }
    }

    public Resource getDocumentById(Long id) {
        try {
            String filePath = "/documents/contracts/" + id + ".des";
            Path file = Paths.get(getClass().getResource(filePath).toURI());
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se puede leer el archivo");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el archivo", e);
        }
    }
}
