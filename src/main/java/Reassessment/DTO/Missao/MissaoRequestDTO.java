package Reassessment.DTO.Missao;
import java.time.LocalDate;
import Reassessment.enums.Prioridade;
import Reassessment.enums.tipoMissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class MissaoRequestDTO {

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

    @NotNull 
    String codigo; 

    @NotNull 
    tipoMissao tipo;
}