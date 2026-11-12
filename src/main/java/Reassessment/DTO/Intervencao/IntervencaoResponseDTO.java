package Reassessment.DTO.Intervencao;
import java.time.LocalDateTime;
import Reassessment.enums.nivelImpacto;
import Reassessment.intervencao.Intervencao;
import lombok.Data;

@Data 
public class IntervencaoResponseDTO {

    Long id;
    Long missaoId;
    Long agenteId;
    String agenteNome;
    LocalDateTime instante;
    String acao;
    String justificativa;
    nivelImpacto impactoEstimado;
    String resultadoObservado;

    public IntervencaoResponseDTO(Intervencao intervencao) {
        this.id = intervencao.getId();
        this.missaoId = intervencao.getMissao().getId();
        this.agenteId = intervencao.getAgente().getId();
        this.agenteNome = intervencao.getAgente().getNome();
        this.instante = intervencao.getInstante();
        this.acao = intervencao.getAcao();
        this.justificativa = intervencao.getJustificativa();
        this.impactoEstimado = intervencao.getImpactoEstimado();
        this.resultadoObservado = intervencao.getResultadoObservado();
    }
}