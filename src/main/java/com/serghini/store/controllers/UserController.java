package com.serghini.store.controllers;

import java.util.Set;

import org.springframework.data.domain.Sort;

import com.serghini.store.dtos.UserDto;
import com.serghini.store.entities.User;
import com.serghini.store.mappers.UserMapper;
import com.serghini.store.repositories.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
	private final UserRepository userRepository;
	private	final UserMapper userMapper;

	@GetMapping("/users")
	
	public Iterable<UserDto>	getAllUsers(@RequestHeader(), @RequestParam(defaultValue="") String sort) {
		if(!Set.of("name", "email").contains(sort))
			sort = "name";
		return userRepository.findAll(Sort.by(sort))
				.stream()
				.map(userMapper::toDto)
				.toList();
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
		User user = userRepository.findById(id).orElse(null);
		if (user == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(userMapper.toDto(user));
	}
}
