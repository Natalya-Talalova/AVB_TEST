package org.example.avb_test.user_service.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.common.dto.CompanyResponceDto;
import org.example.avb_test.user_service.dto.UserCreateDto;
import org.example.common.dto.UserResponseDto;
import org.example.avb_test.user_service.entity.User;
import org.example.avb_test.user_service.exception.EntityNotFoundByIdException;
import org.example.avb_test.user_service.mapper.UserMapper;
import org.example.avb_test.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public List<org.example.common.dto.UserResponseDto> findAll() {
        List<User> entities = repository.findAll();
        return entities.stream()
                .map(this::toResponseDtoWithCompanyName)
                .collect(Collectors.toList());
    }

    @Override
    public org.example.common.dto.UserResponseDto findById(Long id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(User.class, id));
        return toResponseDtoWithCompanyName(entity);
    }

    @Override
    @Transactional
    public org.example.common.dto.UserResponseDto create(UserCreateDto userDto) {
        User entity = mapper.toEntity(userDto);
        entity = repository.save(entity);
        return toResponseDtoWithCompanyName(entity);
    }

    @Override
    @Transactional
    public org.example.common.dto.UserResponseDto update(UserCreateDto userDto, Long id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(User.class, id));
        mapper.update(userDto, entity);
        entity = repository.save(entity);
        return toResponseDtoWithCompanyName(entity);
    }

    @Override
    public void delete(Long id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(User.class, id));
        repository.delete(entity);
    }

    private UserResponseDto toResponseDtoWithCompanyName(User user) {
        UserResponseDto dto = mapper.toDto(user);
        if (user.getCompanyId() != null) {
            String companyServiceUrl = "http://localhost:8081/api/companies/" + user.getCompanyId();
            try {
                RestTemplate restTemplate = new RestTemplate();
                CompanyResponceDto company = restTemplate.getForObject(companyServiceUrl, CompanyResponceDto.class);
                if (company != null) {
                    dto.setCompanyName(company.getName());
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode().value() == 404) {
                    dto.setCompanyName(null);
                } else {
                    throw e;
                }
            }
        }
        return dto;
    }
}
