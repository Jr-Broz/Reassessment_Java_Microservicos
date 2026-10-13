package Reassessment.DTO.Anomalia;
import Reassessment.enums.nivelRisco;
import Reassessment.enums.origemAnomalia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class AnomaliaRequestDTO {

    @NotNull 
    Long eventoID;

    @NotBlank 
    String divergencia;

    @NotNull 
    nivelRisco risco;

    @NotNull 
    origemAnomalia origem; 
    
    String observacoes;
}