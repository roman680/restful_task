package com.example.mapper;

import com.example.model.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.model.User;
import com.example.model.dto.UserRequestDto;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(UserRequestDto userRequestDto, @MappingTarget User user);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    UserResponseDto userToUserResponseDto(User user);
}
