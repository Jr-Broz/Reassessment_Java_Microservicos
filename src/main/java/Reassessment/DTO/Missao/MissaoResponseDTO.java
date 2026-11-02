package Reassessment.DTO.Missao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Reassessment.DTO.Agente.AgenteResponseDTO;
import Reassessment.agente.Agente;
import Reassessment.enums.Prioridade;
import Reassessment.enums.statusMissao;
import Reassessment.enums.tipoMissao;
import Reassessment.missao.Missao;
import lombok.Data;

@Data 
public class MissaoResponseDTO {
 
    Long id;
    String codigo;
    String objetivo;
    Long anomaliaId; //Tava Long antes de estar Anomalia
    LocalDate destinoData;
    String destinoLocal;
    Prioridade prioridade;
    statusMissao status;
    List<AgenteResponseDTO> agentes;
    tipoMissao tipo;


 //problema com esse de anomaliaID, voltar aqui dps

    public MissaoResponseDTO(Missao missao) {
    
    this.id = missao.getId();
    this.codigo = missao.getCodigoMissao();
    this.objetivo = missao.getObjetivo();
    this.anomaliaId = missao.getAnomalia().getId();
    this.destinoData = missao.getDestinoData();
    this.destinoLocal = missao.getDestinoLocal();
    this.prioridade = missao.getPrioridade();
    this.status = missao.getStatus();
    this.agentes = new ArrayList<>();
    this.tipo = missao.getTipo();

        for(Agente a : missao.getAgentes()) {
            this.agentes.add(new AgenteResponseDTO(a));
        }
    }

}