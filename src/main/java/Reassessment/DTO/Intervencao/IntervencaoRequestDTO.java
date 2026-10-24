package Reassessment.DTO.Intervencao;

import Reassessment.enums.nivelImpacto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class IntervencaoRequestDTO {
    

    @NotNull
    Long agenteID;
    
    
    @NotBlank
    String acao;
    


    @NotBlank
    String justificativa;
    
    @NotNull 
    nivelImpacto impactoEstimado;

    String resultadoObservado;

}