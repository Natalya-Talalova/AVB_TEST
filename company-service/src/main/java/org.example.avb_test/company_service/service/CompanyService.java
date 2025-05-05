package org.example.avb_test.company_service.service;

import org.example.avb_test.company_service.dto.CompanyCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.common.dto.CompanyResponceDto;

@Service
public interface CompanyService {
    CompanyResponceDto create(CompanyCreateDto companyDto);
    CompanyResponceDto findById(Long id);
    CompanyResponceDto update(Long id, CompanyCreateDto companyDto);
    void delete(Long id);
    Page<CompanyResponceDto> findAll(Pageable pageable);
}
