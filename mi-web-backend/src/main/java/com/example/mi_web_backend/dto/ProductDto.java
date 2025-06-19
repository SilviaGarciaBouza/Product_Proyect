package com.example.mi_web_backend.dto;
// src/main/java/com/example/mi_web_backend/dto/ProductDto.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Integer stock;
    private Double prize; // ¡Nuevo campo añadido!
    private Long companyId;
    private String companyName;

    // Constructor explícito (Aunque Lombok @AllArgsConstructor ya lo genera)
    public ProductDto(Long id, String name, Integer stock, Double prize, Long companyId, String companyName) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.prize = prize;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    // Getters y Setters explícitos (Aunque Lombok @Data ya los genera)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}