package Reassessment.DTO.Auth;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank
    String username;

    @NotBlank 
    String senha;
}

