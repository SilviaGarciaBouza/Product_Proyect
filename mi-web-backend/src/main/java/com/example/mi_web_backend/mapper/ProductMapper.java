package com.example.mi_web_backend.mapper;


import com.example.mi_web_backend.dto.ProductDto;
import com.example.mi_web_backend.model.Product;
import com.example.mi_web_backend.model.Company; // Necesario para acceder al ID/Nombre de la compañía

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component // Marca esta clase como un componente de Spring para que pueda ser inyectada
public class ProductMapper {

    /**
     * Convierte una entidad Product a un ProductDto.
     * Incluye el mapeo del ID y nombre de la compañía asociada si existe.
     * @param product La entidad Product a convertir.
     * @return El ProductDto resultante, o null si la entidad de entrada es null.
     */
    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        Long companyId = null;
        String companyName = null;

        // --- Lógica RESTAURADA para obtener CompanyId y CompanyName ---
        // Comprueba si la relación con Company está cargada y no es nula
        // Esto es importante si usas FetchType.LAZY para evitar NullPointerExceptions
        if (product.getCompany() != null) {
            companyId = product.getCompany().getId();
            companyName = product.getCompany().getName();
        }


        // Utiliza el constructor de ProductDto para crear el DTO
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getPrize(), // Mapea el campo 'prize'
                companyId,     // Esto es un Long para el ID de la compañía
                companyName    // Esto es un String para el nombre de la compañía
        );
    }

    /**
     * Convierte un ProductDto a una entidad Product.
     * Importante: Este método NO establece la relación con la entidad Company (el objeto Company completo).
     * Esa lógica de búsqueda de la Company por companyId y establecimiento de la relación en la entidad
     * debe realizarse en el Servicio (ProductService).
     * @param productDto El ProductDto a convertir.
     * @return La entidad Product resultante, o null si el DTO de entrada es null.
     */
    public Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDto.getId()); // El ID podría ser nulo para nuevas creaciones, se establecerá después por la BD.
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrize(productDto.getPrize()); // Mapea el campo 'prize'

        // La relación con Company (el objeto Company completo) NO se mapea aquí directamente.
        // Será responsabilidad del servicio buscar la Company por companyId y establecerla en la entidad.
        return product;
    }

    /**
     * Convierte una lista de entidades Product a una lista de ProductDto.
     * @param products La lista de entidades Product a convertir.
     * @return Una lista de ProductDto.
     */
    public List<ProductDto> toDtoList(List<Product> products) {
        return products.stream()
                .map(this::toDto) // Reutiliza el método toDto para cada elemento en el stream
                .collect(Collectors.toList());
    }

    /**
     * Actualiza una entidad Product existente con los datos de un ProductDto.
     * Solo actualiza los campos que no son nulos en el DTO (excluyendo el ID y la relación Company,
     * que deben manejarse en el servicio).
     * @param productDto El DTO con los datos actualizados.
     * @param product La entidad Product existente a actualizar.
     */
    public void updateEntityFromDto(ProductDto productDto, Product product) {
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getStock() != null) {
            product.setStock(productDto.getStock());
        }
        if (productDto.getPrize() != null) { // Actualiza el campo 'prize'
            product.setPrize(productDto.getPrize());
        }
        // Nota: La actualización del ID y la relación con Company se maneja fuera del mapper,
        // típicamente en el servicio, ya que requieren lógica de búsqueda o generación de ID.
    }
}