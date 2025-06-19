package com.example.mi_web_backend.controller;

// src/main/java/com/example/mi_web_backend/controller/CompanyController.java

import com.example.mi_web_backend.dto.CompanyDto;
import com.example.mi_web_backend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/companies") // Define el path base para todos los endpoints de este controlador
public class CompanyController {

    private final CompanyService companyService; // Inyección de dependencia del servicio de Company

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Crea una nueva compañía.
     * POST /api/companies
     * @param companyDto DTO con los datos de la compañía a crear.
     * @return ResponseEntity con el CompanyDto de la compañía creada y HttpStatus 201 (Created).
     */
    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {
        CompanyDto createdCompany = companyService.createCompany(companyDto);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    /**
     * Obtiene todas las compañías.
     * GET /api/companies
     * @return ResponseEntity con una lista de CompanyDto y HttpStatus 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    /**
     * Obtiene una compañía por su ID.
     * GET /api/companies/{id}
     * @param id ID de la compañía a buscar.
     * @return ResponseEntity con el CompanyDto y HttpStatus 200 (OK) si se encuentra,
     * o HttpStatus 404 (Not Found) si no.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id)
                .map(companyDto -> new ResponseEntity<>(companyDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza una compañía existente.
     * PUT /api/companies/{id}
     * @param id ID de la compañía a actualizar.
     * @param companyDto DTO con los datos actualizados de la compañía.
     * @return ResponseEntity con el CompanyDto actualizado y HttpStatus 200 (OK).
     * o HttpStatus 404 (Not Found) si no se encuentra la compañía.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
        try {
            CompanyDto updatedCompany = companyService.updateCompany(id, companyDto);
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Manejo básico de excepciones, podrías crear clases de excepción más específicas
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina una compañía por su ID.
     * DELETE /api/companies/{id}
     * @param id ID de la compañía a eliminar.
     * @return ResponseEntity con HttpStatus 204 (No Content) si se elimina con éxito,
     * o HttpStatus 404 (Not Found) si no se encuentra la compañía.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica éxito sin contenido de respuesta
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}