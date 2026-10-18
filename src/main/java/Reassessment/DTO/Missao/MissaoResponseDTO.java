package Reassessment.DTO.Missao;

import java.time.LocalDate;
import java.util.List;

import Reassessment.agente.Agente;
import Reassessment.anomalia.Anomalia;
import Reassessment.enums.Prioridade;
import Reassessment.enums.statusMissao;
import Reassessment.missao.Missao;

public class MissaoResponseDTO {
 
    Long id;
    String codigo;
    String objetivo;
    Anomalia anomaliaId; //Tava Long antes de estar Anomalia
    LocalDate destinoData;
    String destinoLocal;
    Prioridade prioridade;
    statusMissao status;
    List<Agente> agente;



 //problema com esse de anomaliaID, voltar aqui dps

    public MissaoResponseDTO(Missao missao) {
    
    this.id = missao.getId();
    this.codigo = missao.getCodigoMissao();
    this.objetivo = missao.getObjetivo();
    this.anomaliaId = missao.getAnomalia();
    this.destinoData = missao.getDestinoData();
    this.destinoLocal = missao.getDestinoLocal();
    this.prioridade = missao.getPrioridade();
    this.status = missao.getStatus();
    this.agente = missao.getAgentes();

    }

}