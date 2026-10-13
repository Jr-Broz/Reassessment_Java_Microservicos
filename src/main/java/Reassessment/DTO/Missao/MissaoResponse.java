package Reassessment.DTO.Missao;

import java.time.LocalDate;
import java.util.List;

import Reassessment.DTO.Agente.AgenteResponseDTO;
import Reassessment.enums.Prioridade;
import Reassessment.enums.statusMissao;

public class MissaoResponse {
 
    Long id;
    String codigo;
    String objetivo;
    Long anomaliaId;
    LocalDate destinoData;
    String destinoLocal;
    Prioridade prioridade;
    statusMissao status;
    List<AgenteResponseDTO> agente;

}