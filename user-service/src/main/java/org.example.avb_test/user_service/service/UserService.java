package org.example.avb_test.user_service.service;

import org.example.avb_test.user_service.dto.UserCreateDto;
import org.example.common.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserResponseDto> findAll();
    UserResponseDto findById(Long id);
    UserResponseDto create(UserCreateDto userDto);
    UserResponseDto update(UserCreateDto userDto, Long id);
    void delete(Long id);
}
