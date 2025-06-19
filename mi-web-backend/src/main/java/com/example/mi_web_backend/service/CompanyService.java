package com.example.mi_web_backend.service;


import com.example.mi_web_backend.dto.CompanyDto;
import com.example.mi_web_backend.model.Company;
import com.example.mi_web_backend.repository.CompanyDao;
import com.example.mi_web_backend.mapper.CompanyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marca esta clase como un componente de servicio de Spring
public class CompanyService {

    private final CompanyDao companyDao; // Inyección de dependencia del repositorio
    private final CompanyMapper companyMapper; // Inyección de dependencia del mapper

    // Constructor para inyección de dependencia
    @Autowired
    public CompanyService(CompanyDao companyDao, CompanyMapper companyMapper) {
        this.companyDao = companyDao;
        this.companyMapper = companyMapper;
    }

    /**
     * Crea una nueva Company en la base de datos.
     * @param companyDto DTO con los datos de la nueva Company. El ID debe ser nulo.
     * @return CompanyDto de la Company creada con su ID asignado.
     */
    public CompanyDto createCompany(CompanyDto companyDto) {
        // Asegurarse de que el ID es nulo para una nueva creación, será generado por la BD.
        companyDto.setId(null);
        Company companyToSave = companyMapper.toEntity(companyDto);
        // Aquí podrías añadir lógica de negocio, por ejemplo, hashear la contraseña
        // companyToSave.setPassword(passwordEncoder.encode(companyToSave.getPassword()));
        Company savedCompany = companyDao.save(companyToSave);
        return companyMapper.toDto(savedCompany);
    }

    /**
     * Obtiene una lista de todas las Companies.
     * @return Lista de CompanyDto.
     */
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyDao.findAll();
        return companyMapper.toDtoList(companies);
    }

    /**
     * Obtiene una Company específica por su ID.
     * @param id ID de la Company a buscar.
     * @return Optional que contiene el CompanyDto si se encuentra, o vacío si no.
     */
    public Optional<CompanyDto> getCompanyById(Long id) {
        return companyDao.findById(id)
                .map(companyMapper::toDto); // Mapea la entidad a DTO si la encuentra
    }

    /**
     * Actualiza una Company existente.
     * @param id ID de la Company a actualizar.
     * @param companyDto DTO con los datos actualizados de la Company.
     * @return CompanyDto de la Company actualizada.
     * @throws RuntimeException si la Company no es encontrada.
     */
    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        return companyDao.findById(id).map(existingCompany -> {
            // Usa el mapper para actualizar los campos de la entidad existente desde el DTO
            companyMapper.updateEntityFromDto(companyDto, existingCompany);

            // Si la contraseña se va a actualizar, deberías hashearla aquí antes de guardar
            // if (companyDto.getPassword() != null && !companyDto.getPassword().isEmpty()) {
            //     existingCompany.setPassword(passwordEncoder.encode(companyDto.getPassword()));
            // }

            Company updatedCompany = companyDao.save(existingCompany);
            return companyMapper.toDto(updatedCompany);
        }).orElseThrow(() -> new RuntimeException("Company not found with id: " + id)); // Lanza excepción si no existe
    }

    /**
     * Elimina una Company por su ID.
     * @param id ID de la Company a eliminar.
     * @throws RuntimeException si la Company no es encontrada.
     */
    public void deleteCompany(Long id) {
        if (!companyDao.existsById(id)) {
            throw new RuntimeException("Company not found with id: " + id);
        }
        companyDao.deleteById(id);
    }
}