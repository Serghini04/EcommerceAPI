package com.serghini.store.mappers;

import com.serghini.store.dtos.UserDto;
import com.serghini.store.entities.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.serghini.store.dtos.RegisterUserRequest;
import com.serghini.store.dtos.UpdateUserRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target="createdAt", expression="java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);
    User    toEntity(RegisterUserRequest request);
    void    update(UpdateUserRequest request, @MappingTarget User user);
}