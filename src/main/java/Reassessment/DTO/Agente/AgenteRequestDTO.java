package Reassessment.DTO.Agente;
import Reassessment.enums.Especialidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class AgenteRequestDTO {

    // Ver de refatorar o código , adicionar o alteracao status?

    @NotBlank  // length maior que 0
    String nome;

    @NotBlank 
    String matricula;

    @NotNull 
    Especialidade especialidade;
}