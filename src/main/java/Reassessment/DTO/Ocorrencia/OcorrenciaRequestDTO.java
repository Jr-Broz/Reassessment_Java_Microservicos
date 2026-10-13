package Reassessment.DTO.Ocorrencia;

import Reassessment.enums.tipoOcorrencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OcorrenciaRequestDTO {
 
    @NotNull
    Long agenteID;

    @NotNull
    tipoOcorrencia tipo;
    
    @NotBlank
    String descricao;
}