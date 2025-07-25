package com.serghini.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddIteamToCartRequest {
    @NotBlank
    @NotEmpty
    private Long productId;
}
