package Reassessment.DTO.Anomalia;

import Reassessment.enums.statusAnomalia;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class AlteracaoStatusAnomaliaRequest {

    @NotNull 
    statusAnomalia status; 

}
