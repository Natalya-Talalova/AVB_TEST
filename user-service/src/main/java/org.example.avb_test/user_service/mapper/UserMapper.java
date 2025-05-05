package org.example.avb_test.user_service.mapper;

import org.example.avb_test.user_service.dto.UserCreateDto;
import org.example.common.dto.UserResponseDto;
import org.example.avb_test.user_service.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreateDto userDto);

    UserResponseDto toDto(User user);

    List<UserResponseDto> toDtoList(List<User> users);

    List<User> toEntityList(List<UserCreateDto> userDtos);

    void update(UserCreateDto userDto, @MappingTarget User user);
}
