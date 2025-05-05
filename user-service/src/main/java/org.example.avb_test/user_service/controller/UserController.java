package org.example.avb_test.user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.avb_test.user_service.dto.UserCreateDto;
import org.example.common.dto.UserResponseDto;
import org.example.avb_test.user_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        log.info("Get all users");
        Page<UserResponseDto> users = userService.findAll(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        log.info("Get user by id: {}", id);
        UserResponseDto user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userDto) {
        log.info("Create user");
        UserResponseDto createdUser = userService.create(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserCreateDto userDto, @PathVariable Long id) {
        log.info("Update user by id: {}", id);
        UserResponseDto updatedUser = userService.update(userDto, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Delete user by id: {}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
