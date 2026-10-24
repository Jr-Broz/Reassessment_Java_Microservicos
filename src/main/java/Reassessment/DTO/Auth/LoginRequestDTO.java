package Reassessment.DTO.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class LoginRequestDTO {

    @NotBlank
    String username;

    @NotBlank 
    String senha;
}

