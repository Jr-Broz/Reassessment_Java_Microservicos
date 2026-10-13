package Reassessment.DTO.Agente;
import Reassessment.enums.statusAgente;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class AlteracaoStatusAgenteRequest {
    
    @NotNull
    statusAgente statusAgente;
}