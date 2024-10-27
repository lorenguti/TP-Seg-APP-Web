package com.example.demo.model;

import java.time.LocalDate;

public class Document {
    private Long id;
    private String type;
    private LocalDate issueDate;

    // Constructor
    public Document(Long id, String type, LocalDate issueDate) {
        this.id = id;
        this.type = type;
        this.issueDate = issueDate;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
}
