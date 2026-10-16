package Reassessment.DTO.Anomalia;

import Reassessment.enums.nivelRisco;
import jakarta.validation.constraints.NotNull;

public class AlteracaoRiscoRequest {

 @NotNull 
 nivelRisco risco;

}
