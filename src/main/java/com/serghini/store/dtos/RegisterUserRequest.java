package com.serghini.store.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message="Name is Required")
    @Size(max=255, message="name must be less than 255 characters")
    private String name;
    @NotBlank(message="Email is Required")
    @Email(message="Email must be valid")
    private String email;
    @NotBlank(message="Password is Required")
    @Size(min=6, max=25, message="Password must be between 6 to 25 characters long.")
    private String password;    
}
