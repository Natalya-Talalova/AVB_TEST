package org.example.avb_test.company_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.avb_test.company_service.dto.CompanyCreateDto;
import org.example.avb_test.company_service.service.CompanyService;
import org.example.common.dto.CompanyResponceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;
    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

    @GetMapping
    public ResponseEntity<Page<CompanyResponceDto>> getAllCompanies(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        log.info("Get page of companies: {}", pageable);
        Page<CompanyResponceDto> companies = companyService.findAll(pageable);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponceDto> getCompanyById(@PathVariable Long id) {
        log.info("Get company by id: {}", id);
        CompanyResponceDto company = companyService.findById(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponceDto> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyCreateDto companyDto) {
        log.info("Update company by id: {}", id);
        CompanyResponceDto updatedCompany = companyService.update(id, companyDto);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        log.info("Delete company by id: {}", id);
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CompanyResponceDto> createCompany(@Valid @RequestBody CompanyCreateDto companyDto) {
        log.info("Create company");
        CompanyResponceDto createdCompany = companyService.create(companyDto);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }
}
