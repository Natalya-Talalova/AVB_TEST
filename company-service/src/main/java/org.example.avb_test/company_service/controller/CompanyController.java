package org.example.avb_test.company_service.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.avb_test.company_service.dto.CompanyCreateDto;
import org.example.avb_test.company_service.service.CompanyService;
import org.example.common.dto.CompanyResponceDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@AllArgsConstructor
public class CompanyController {

    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyResponceDto>> getAllCompanies() {
        List<CompanyResponceDto> companies = companyService.findAll();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponceDto> getCompanyById(@PathVariable Long id) {
        CompanyResponceDto company = companyService.findById(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponceDto> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyCreateDto companyDto) {
        CompanyResponceDto updatedCompany = companyService.update(id, companyDto);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CompanyResponceDto> createCompany(@Valid @RequestBody CompanyCreateDto companyDto) {
        CompanyResponceDto createdCompany = companyService.create(companyDto);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }
}
