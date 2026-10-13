package Reassessment.DTO.Missao;
import java.time.LocalDate;
import Reassessment.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MissaoRequest {

    @NotBlank 
    String objetivo;

    @NotNull 
    Long anomaliaId;

    @NotNull 
    LocalDate destinoData; // Ver pq não mudar pra string tbm

    @NotBlank 
    String destinoLocal;

    @NotNull 
    Prioridade prioridade;
}