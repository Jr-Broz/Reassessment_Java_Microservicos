package Reassessment.DTO.Agente;

import Reassessment.agente.Agente;
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


    public AgenteResponseDTO(Agente agente) {

        this.id = agente.getId();
        this.nome = agente.getNome();
        this.matricula = agente.getMatricula();
        this.especialidade = agente.getEspecialidade();
        this.statusAgente = agente.getStatusDoAgente(); 
    }
}