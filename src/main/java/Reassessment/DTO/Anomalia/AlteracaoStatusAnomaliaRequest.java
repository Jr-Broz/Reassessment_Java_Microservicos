package Reassessment.DTO.Anomalia;

import Reassessment.enums.statusAnomalia;
import jakarta.validation.constraints.NotNull;

public class AlteracaoStatusAnomaliaRequest {

    @NotNull 
    statusAnomalia status; 

}
