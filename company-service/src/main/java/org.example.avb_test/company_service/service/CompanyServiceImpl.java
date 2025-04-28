package org.example.avb_test.company_service.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.avb_test.company_service.dto.CompanyCreateDto;
import org.example.common.dto.CompanyResponceDto;
import org.example.avb_test.company_service.entity.Company;
import org.example.avb_test.company_service.mapper.CompanyMapper;
import org.example.avb_test.company_service.repository.CompanyRepository;
import org.example.avb_test.company_service.exception.EntityNotFoundByIdException;
import org.example.common.dto.UserResponseDto;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    public CompanyServiceImpl(CompanyRepository repository, CompanyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CompanyResponceDto create(CompanyCreateDto companyDto) {
        Company entity = mapper.toEntity(companyDto);
        entity = repository.save(entity);
        return toResponseDtoWithEmployees(entity);
    }

    @Override
    public CompanyResponceDto findById(Long id) {
        Company entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(Company.class, id));
        return toResponseDtoWithEmployees(entity);
    }

    @Override
    public CompanyResponceDto update(Long id, CompanyCreateDto companyDto) {
        Company entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(Company.class, id));
        mapper.update(companyDto, entity);
        entity = repository.save(entity);
        return toResponseDtoWithEmployees(entity);
    }

    @Override
    public List<CompanyResponceDto> findAll() {
        List<Company> entities = repository.findAll();
        return entities.stream()
                .map(this::toResponseDtoWithEmployees)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.delete(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(Company.class, id)));
    }

    private CompanyResponceDto toResponseDtoWithEmployees(Company company) {
        CompanyResponceDto dto = mapper.toDto(company);
        List<UserResponseDto> employees = company.getEmployeeId() != null
                ? company.getEmployeeId().stream()
                .map(this::fetchUserById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                : List.of();
        dto.setEmployees(employees);
        return dto;
    }

    private UserResponseDto fetchUserById(Long userId) {
        String userServiceUrl = "http://localhost:8080/api/users/" + userId;
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(userServiceUrl, UserResponseDto.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return null;
            }
            throw e;
        }
    }
}
