package com.example.mi_web_backend.model;

// src/main/java/com/example/mi_web_backend/model/Product.java

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 28) // Mapea a la columna 'name' con un límite de longitud
    private String name;

    @Column(name = "stock") // Mapea a la columna 'stock'
    private Integer stock;

    @Column(name = "prize") // Mapea a la columna 'prize'
    private Double prize;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY es recomendable para relaciones, carga la Company solo cuando se accede a ella
    @JoinColumn(name = "company", nullable = false)
    private Company company;

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}