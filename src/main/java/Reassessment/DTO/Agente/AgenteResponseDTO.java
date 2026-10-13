package Reassessment.DTO.Agente;

import Reassessment.enums.Especialidade;
import Reassessment.enums.statusAgente;
import lombok.Data;

@Data 
public class AgenteResponseDTO {
 
    Long id;
    String nome;
    String matricula;
    Especialidade especialidade;
    statusAgente statusAgente;

}