package Reassessment.DTO.Anomalia;

import Reassessment.enums.nivelRisco;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class AlteracaoRiscoRequest {

 @NotNull 
 nivelRisco risco;

}
