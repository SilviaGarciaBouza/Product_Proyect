package com.example.mi_web_backend.repository;

import com.example.mi_web_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
// JpaRepository<TipoDeEntidad, TipoDeLaClavePrimariaDeLaEntidad>

    // Spring Data JPA automáticamente proporciona métodos CRUD como:
    // - save(entity): Guarda (inserta o actualiza) una entidad
    // - findById(id): Busca una entidad por su ID
    // - findAll(): Obtiene todas las entidades
    // - deleteById(id): Elimina una entidad por su ID
    // - etc.

    // Se pueden añadir métodos personalizados:
    // List<Product> findByNameContainingIgnoreCase(String name);
    // List<Product> findByStockLessThan(Integer stock);
    List<Product> findByCompanyId(Long companyId);
}
