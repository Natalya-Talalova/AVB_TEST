package org.example.avb_test.user_service.service;

import org.example.avb_test.user_service.dto.UserCreateDto;
import org.example.common.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Page<UserResponseDto> findAll(Pageable pageable);
    UserResponseDto findById(Long id);
    UserResponseDto create(UserCreateDto userDto);
    UserResponseDto update(UserCreateDto userDto, Long id);
    void delete(Long id);
}
