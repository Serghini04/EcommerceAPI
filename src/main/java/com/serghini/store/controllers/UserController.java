package com.serghini.store.controllers;

import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import com.serghini.store.dtos.ChangePasswordRequest;
import com.serghini.store.dtos.RegisterUserRequest;
import com.serghini.store.dtos.UpdateUserRequest;
import com.serghini.store.dtos.UserDto;
import com.serghini.store.entities.User;
import com.serghini.store.mappers.UserMapper;
import com.serghini.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@AllArgsConstructor
public class UserController {
	private final UserRepository userRepository;
	private	final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/users")
	
	public Iterable<UserDto>	getAllUsers(@RequestParam(defaultValue="") String sort) {
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

	
    @PostMapping("/users")
    public  ResponseEntity<?> createUser(@Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder uriBuilder) {
		
		if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body(Map.of("Email", "Email is already registered"));
		}
		
		var user = userMapper.toEntity(request);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		var userDto = userMapper.toDto(user);
		var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
		return ResponseEntity.created(uri).body(userDto);
    }

	@PutMapping("/users/{id}")
	public ResponseEntity<UserDto>	updateUser(@PathVariable(name = "id") Long id, @RequestBody UpdateUserRequest request) {
		User user = userRepository.findById(id).orElse(null);

		if (user == null)
			return ResponseEntity.notFound().build();
		userMapper.update(request, user);
		userRepository.save(user);
		return ResponseEntity.ok(userMapper.toDto(user));
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void>	deleteUser(@PathVariable(name = "id") Long id) {
		User	user = userRepository.findById(id).orElse(null);
		if (user == null)
			return ResponseEntity.notFound().build();
		userRepository.delete(user);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/users/{id}/change-password")
	public ResponseEntity<Void>	changePassword(@PathVariable(name = "id") Long id, @RequestBody ChangePasswordRequest request) {
		User	user = userRepository.findById(id).orElse(null);

		if (user == null)
			return ResponseEntity.notFound().build();
		if (user.getPassword() != request.getOldPassword())
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		user.setPassword(request.getNewPassword());
		userRepository.save(user);
		return ResponseEntity.noContent().build();
	}
}
