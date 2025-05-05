package org.example.avb_test.user_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.common.service.CompanyServiceClient;
import org.example.avb_test.user_service.dto.UserCreateDto;
import org.example.common.dto.UserResponseDto;
import org.example.avb_test.user_service.entity.User;
import org.example.avb_test.user_service.exception.EntityNotFoundByIdException;
import org.example.avb_test.user_service.mapper.UserMapper;
import org.example.avb_test.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final CompanyServiceClient companyClient;

    private User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(User.class, id));
    }

    @Override
    public Page<UserResponseDto> findAll(Pageable pageable) {
        Page<User> entities = repository.findAll(pageable);
        log.info("Get list of all users");
        return entities.map(mapper::toDto);
    }

    @Override
    public UserResponseDto findById(Long id) {
        User entity = getUserById(id);
        log.info("Get user with id: {}", entity.getId());
        return toResponseDtoWithCompanyName(entity);
    }

    @Override
    @Transactional
    public UserResponseDto create(UserCreateDto userDto) {
        User entity = mapper.toEntity(userDto);
        entity = repository.save(entity);
        log.info("Created user with id: {}", entity.getId());
        return toResponseDtoWithCompanyName(entity);
    }

    @Override
    @Transactional
    public UserResponseDto update(UserCreateDto userDto, Long id) {
        User entity = getUserById(id);
        mapper.update(userDto, entity);
        entity = repository.save(entity);
        log.info("Updated user with id: {}", entity.getId());
        return toResponseDtoWithCompanyName(entity);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Delete user with id: {}", repository.findById(id));
        User entity = getUserById(id);
        repository.delete(entity);
    }

    private UserResponseDto toResponseDtoWithCompanyName(User user) {
        UserResponseDto dto = mapper.toDto(user);

        if (user.getCompanyId() != null) {
            var company = companyClient.getCompanyById(user.getCompanyId());
            dto.setCompanyName(company != null ? company.getName() : null);
        }

        return dto;
    }
}
