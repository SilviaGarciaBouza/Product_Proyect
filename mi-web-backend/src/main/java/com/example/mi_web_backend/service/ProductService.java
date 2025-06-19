package com.example.mi_web_backend.service;
// src/main/java/com/example/mi_web_backend/service/ProductService.java

import com.example.mi_web_backend.dto.ProductDto;
import com.example.mi_web_backend.model.Company;
import com.example.mi_web_backend.model.Product;
import com.example.mi_web_backend.repository.ProductDao;
import com.example.mi_web_backend.repository.CompanyDao; // Necesario para la relación con Company
import com.example.mi_web_backend.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marca esta clase como un componente de servicio de Spring
public class ProductService {

    private final ProductDao productDao; // Inyección de dependencia del repositorio de productos
    private final CompanyDao companyDao; // Inyección de dependencia del repositorio de compañías (para FK)
    private final ProductMapper productMapper; // Inyección de dependencia del mapper de productos

    // Constructor para inyección de dependencia
    @Autowired
    public ProductService(ProductDao productDao, CompanyDao companyDao, ProductMapper productMapper) {
        this.productDao = productDao;
        this.companyDao = companyDao;
        this.productMapper = productMapper;
    }

    /**
     * Crea un nuevo Producto en la base de datos.
     * Requiere que el companyId en el DTO sea válido y no nulo.
     * @param productDto DTO con los datos del nuevo Producto. El ID debe ser nulo.
     * @return ProductDto del Producto creado con su ID asignado.
     * @throws RuntimeException si la Company asociada no es encontrada o companyId es nulo.
     */
    public ProductDto createProduct(ProductDto productDto) {
        // Asegurar que el ID es nulo para una nueva creación, será generado por la BD.
        productDto.setId(null);

        // Lógica para establecer la relación con Company
        if (productDto.getCompanyId() == null) {
            throw new RuntimeException("Company ID is required to create a Product.");
        }

        // Buscar la entidad Company por su ID. Si no existe, lanzar excepción.
        Company company = companyDao.findById(productDto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found for ID: " + productDto.getCompanyId()));

        // Mapear el DTO a la entidad Product. El mapper no maneja la relación Company, solo los campos directos.
        Product productToSave = productMapper.toEntity(productDto);

        // Establecer la relación de la entidad Product con la entidad Company encontrada.
        productToSave.setCompany(company); // Este método espera un objeto Company

        // Guardar la nueva entidad Product en la base de datos.
        Product savedProduct = productDao.save(productToSave);

        // Mapear la entidad guardada de vuelta a un DTO para la respuesta.
        return productMapper.toDto(savedProduct);
    }

    /**
     * Obtiene una lista de todos los Productos.
     * @return Lista de ProductDto.
     */
    public List<ProductDto> getAllProducts() {
        List<Product> products = productDao.findAll();
        // El mapper ahora incluirá companyId y companyName en los DTOs si la relación está cargada.
        return productMapper.toDtoList(products);
    }

    /**
     * Obtiene un Producto específico por su ID.
     * @param id ID del Producto a buscar.
     * @return Optional que contiene el ProductDto si se encuentra, o vacío si no.
     */
    public Optional<ProductDto> getProductById(Long id) {
        return productDao.findById(id)
                .map(productMapper::toDto); // Mapea la entidad a DTO si la encuentra
    }

    /**
     * Obtiene una lista de Productos asociados a una Company específica.
     * @param companyId ID de la Company.
     * @return Lista de ProductDto.
     * @throws RuntimeException si la Company no es encontrada.
     */
    public List<ProductDto> getProductsByCompanyId(Long companyId) {
        // Primero, verifica si la compañía existe. Si no, lanza una excepción.
        companyDao.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));

        // Busca productos por el ID de la compañía.
        List<Product> products = productDao.findByCompanyId(companyId);

        // Mapea la lista de entidades a una lista de DTOs.
        return productMapper.toDtoList(products);
    }

    /**
     * Actualiza un Producto existente.
     * @param id ID del Producto a actualizar.
     * @param productDto DTO con los datos actualizados del Producto.
     * @return ProductDto del Producto actualizado.
     * @throws RuntimeException si el Producto no es encontrado o la nueva Company no es válida.
     */
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        return productDao.findById(id).map(existingProduct -> {
            // Usa el mapper para actualizar los campos simples (name, stock, prize) de la entidad existente desde el DTO
            productMapper.updateEntityFromDto(productDto, existingProduct);

            // Lógica para actualizar la relación con Company si el companyId en el DTO es diferente o se proporciona.
            // Esto evita una búsqueda de DB si el companyId no ha cambiado.
            if (productDto.getCompanyId() != null &&
                    (existingProduct.getCompany() == null || !productDto.getCompanyId().equals(existingProduct.getCompany().getId()))) {

                // Busca la nueva entidad Company. Si no existe, lanza excepción.
                Company newCompany = companyDao.findById(productDto.getCompanyId())
                        .orElseThrow(() -> new RuntimeException("New Company not found for ID: " + productDto.getCompanyId()));

                // Establece la nueva relación de la entidad Product con la entidad Company.
                existingProduct.setCompany(newCompany); // Este método espera un objeto Company
            }

            // Guarda la entidad Product actualizada en la base de datos.
            Product updatedProduct = productDao.save(existingProduct);

            // Mapea la entidad actualizada de vuelta a un DTO para la respuesta.
            return productMapper.toDto(updatedProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id)); // Lanza excepción si el producto no existe
    }

    /**
     * Elimina un Producto por su ID.
     * @param id ID del Producto a eliminar.
     * @throws RuntimeException si el Producto no es encontrado.
     */
    public void deleteProduct(Long id) {
        if (!productDao.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productDao.deleteById(id);
    }
}