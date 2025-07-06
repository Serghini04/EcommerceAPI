package com.serghini.store.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String name;
	private String email;
	@JsonFormat(pattern="yyyy-mm-dd hh:mm:ss")
	private LocalDateTime createdAt;
	@JsonIgnore
	private String password;
}