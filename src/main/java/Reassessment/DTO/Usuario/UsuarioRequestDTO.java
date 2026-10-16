package Reassessment.DTO.Usuario;

import java.util.Optional;

import Reassessment.enums.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {
    
    @NotBlank 
    String username;
    
    @NotBlank
    @Size(min = 6)
    String senha;

    @NotNull 
    Perfil perfil;

    
    Optional <Long> agenteId;

}
