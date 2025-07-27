package com.serghini.store.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthDto {
    @Email
    @NotBlank(message="Email is Required1")
    private String email;

    @NotBlank(message="Password is Required")
    @Size(min=6, max=25, message="Password must be between 6 to 25 characters long.")
    private String password;
}
