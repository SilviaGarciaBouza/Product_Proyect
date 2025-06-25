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


}