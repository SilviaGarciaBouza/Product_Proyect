package com.example.mi_web_backend.controller;
// src/main/java/com/example/mi_web_backend/controller/ProductController.java

import com.example.mi_web_backend.dto.ProductDto;
import com.example.mi_web_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/products") // Define el path base para todos los endpoints de este controlador
public class ProductController {

    private final ProductService productService; // Inyección de dependencia del servicio de Product

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Crea un nuevo producto.
     * POST /api/products
     * @param productDto DTO con los datos del producto a crear (debe incluir companyId).
     * @return ResponseEntity con el ProductDto del producto creado y HttpStatus 201 (Created).
     * @throws RuntimeException si companyId no es válido o la compañía no existe.
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        try {
            ProductDto createdProduct = productService.createProduct(productDto);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Podrías mapear esta excepción a un HttpStatus 400 (Bad Request) o 404 (Not Found)
            // dependiendo de la causa (ej: companyId nulo o no encontrado).
            // Para simplicidad, se retorna 400.
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todos los productos.
     * GET /api/products
     * @return ResponseEntity con una lista de ProductDto y HttpStatus 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Obtiene un producto por su ID.
     * GET /api/products/{id}
     * @param id ID del producto a buscar.
     * @return ResponseEntity con el ProductDto y HttpStatus 200 (OK) si se encuentra,
     * o HttpStatus 404 (Not Found) si no.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(productDto -> new ResponseEntity<>(productDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Obtiene productos por el ID de la compañía a la que pertenecen.
     * GET /api/products/by-company/{companyId}
     * @param companyId ID de la compañía.
     * @return ResponseEntity con una lista de ProductDto y HttpStatus 200 (OK).
     * o HttpStatus 404 (Not Found) si la compañía no existe.
     */
    @GetMapping("/by-company/{companyId}")
    public ResponseEntity<List<ProductDto>> getProductsByCompanyId(@PathVariable Long companyId) {
        try {
            List<ProductDto> products = productService.getProductsByCompanyId(companyId);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Actualiza un producto existente.
     * PUT /api/products/{id}
     * @param id ID del producto a actualizar.
     * @param productDto DTO con los datos actualizados del producto.
     * @return ResponseEntity con el ProductDto actualizado y HttpStatus 200 (OK).
     * o HttpStatus 404 (Not Found) si no se encuentra el producto o la compañía asociada.
     * o HttpStatus 400 (Bad Request) si hay otros problemas.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        try {
            ProductDto updatedProduct = productService.updateProduct(id, productDto);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Manejo de excepciones más detallado aquí sería recomendable
            if (e.getMessage().contains("not found")) { // Simplificado para el ejemplo
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un producto por su ID.
     * DELETE /api/products/{id}
     * @param id ID del producto a eliminar.
     * @return ResponseEntity con HttpStatus 204 (No Content) si se elimina con éxito,
     * o HttpStatus 404 (Not Found) si no se encuentra el producto.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica éxito sin contenido de respuesta
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}