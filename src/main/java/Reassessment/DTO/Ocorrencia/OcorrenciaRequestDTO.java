package Reassessment.DTO.Ocorrencia;

import java.util.Map;

import Reassessment.enums.tipoOcorrencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class OcorrenciaRequestDTO {
 
    @NotNull
    Long agenteId;

    @NotNull
    tipoOcorrencia tipo;
    
    @NotBlank
    String descricao;
    
    Map<String, Object> detalhes;
}