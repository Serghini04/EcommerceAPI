package com.serghini.store.controllers;

import com.serghini.store.dtos.UserDto;
import com.serghini.store.entities.User;
import com.serghini.store.mappers.UserMapper;
import com.serghini.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
	private final UserRepository userRepository;
	private	final UserMapper userMapper;

	@RequestMapping("/users")
	public Iterable<UserDto>	getAllUsers() {
		return userRepository.findAll()
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
