package com.serghini.store.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RegisterUserRequest {
    private String name;
    private String email;
    private String password;    
}
