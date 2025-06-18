package com.serghini.store.mappers;

import com.serghini.store.dtos.UserDto;
import com.serghini.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
	@Mapping(target = "createdAt", expression="java(java.time.LocalDateTime.now())")
	UserDto toDto(User user);
}

