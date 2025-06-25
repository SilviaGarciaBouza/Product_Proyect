package com.example.mi_web_backend.dto;


// src/main/java/com/example/mi_web_backend/dto/CompanyDto.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class CompanyDto {
    private Long id; // Puede ser null para creación, usado para actualización y respuesta
    private String name;
    private String address;


}