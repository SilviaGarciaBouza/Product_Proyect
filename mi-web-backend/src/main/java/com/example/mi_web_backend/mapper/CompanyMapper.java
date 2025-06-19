package com.example.mi_web_backend.mapper;


// src/main/java/com/example/mi_web_backend/mapper/CompanyMapper.java

import com.example.mi_web_backend.dto.CompanyDto;
import com.example.mi_web_backend.model.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component // Para que Spring pueda inyectar esta clase
public class CompanyMapper {

    public CompanyDto toDto(Company company) {
        if (company == null) {
            return null;
        }
        return new CompanyDto(company.getId(), company.getName(), company.getAddress());
    }

    public Company toEntity(CompanyDto companyDto) {
        if (companyDto == null) {
            return null;
        }
        Company company = new Company();
        company.setId(companyDto.getId());
        company.setName(companyDto.getName());
        company.setAddress(companyDto.getAddress());
        return company;
    }

    public List<CompanyDto> toDtoList(List<Company> companies) {
        return companies.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Método para actualizar una entidad existente desde un DTO (útil para PUT/PATCH)
    public void updateEntityFromDto(CompanyDto companyDto, Company company) {
        if (companyDto.getName() != null) {
            company.setName(companyDto.getName());
        }
        if (companyDto.getAddress() != null) {
            company.setAddress(companyDto.getAddress());
        }
        // No actualizamos el ID aquí, ya que es el identificador de la entidad existente.
    }
}