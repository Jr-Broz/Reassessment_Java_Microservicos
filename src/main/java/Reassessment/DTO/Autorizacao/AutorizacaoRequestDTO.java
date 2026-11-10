package Reassessment.DTO.Autorizacao;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 

public class AutorizacaoRequestDTO {

    @NotBlank 
    String observacao;
}
