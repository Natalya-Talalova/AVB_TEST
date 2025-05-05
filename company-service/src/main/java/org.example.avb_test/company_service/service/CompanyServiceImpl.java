package org.example.avb_test.company_service.service;

import lombok.RequiredArgsConstructor;
import org.example.common.service.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.example.avb_test.company_service.dto.CompanyCreateDto;
import org.example.common.dto.CompanyResponceDto;
import org.example.avb_test.company_service.entity.Company;
import org.example.avb_test.company_service.mapper.CompanyMapper;
import org.example.avb_test.company_service.repository.CompanyRepository;
import org.example.avb_test.company_service.exception.EntityNotFoundByIdException;
import org.example.common.dto.UserResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    private final CompanyMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private final UserServiceClient restClient;

    private Company getCompanyById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(Company.class, id));
    }

    @Transactional
    @Override
    public CompanyResponceDto create(CompanyCreateDto companyDto) {
        Company entity = mapper.toEntity(companyDto);
        entity = repository.save(entity);
        log.info("Created company with id: {}", entity.getId());
        return toResponseDtoWithEmployees(entity);
    }

    @Override
    public CompanyResponceDto findById(Long id) {
        Company entity = getCompanyById(id);
        log.info("Get company with id: {}", entity.getId());
        return toResponseDtoWithEmployees(entity);
    }

    @Transactional
    @Override
    public CompanyResponceDto update(Long id, CompanyCreateDto companyDto) {
        Company entity = getCompanyById(id);
        mapper.update(companyDto, entity);
        entity = repository.save(entity);
        log.info("Updated company with id: {}", entity.getId());
        return toResponseDtoWithEmployees(entity);
    }

    @Override
    public Page<CompanyResponceDto> findAll(Pageable pageable) {
        Page<Company> entities = repository.findAll(pageable);
        log.info("Get list of all companies");
        return entities.map(mapper::toDto);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Delete company with id: {}", repository.findById(id));
        repository.delete(getCompanyById(id));
    }

    private CompanyResponceDto toResponseDtoWithEmployees(Company company) {
        CompanyResponceDto dto = mapper.toDto(company);

        List<Long> ids = company.getEmployeeId();
        List<UserResponseDto> employees = (ids != null && !ids.isEmpty())
                ? restClient.fetchUsersByIds(ids)
                : List.of();

        dto.setEmployees(employees);
        log.info("Fetched {} employees for company id {}", employees.size(), company.getId());
        return dto;
    }
}

