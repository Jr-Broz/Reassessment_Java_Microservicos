package Reassessment.DTO.Ocorrencia;

import java.time.LocalDateTime;
import java.util.Map;

import Reassessment.enums.tipoOcorrencia;
import Reassessment.ocorrencia.Ocorrencia;
import lombok.Data;

@Data 
public class OcorrenciaResponseDTO {

    String id;
    Long missaoId;
    Long agenteId;
    String agenteNome;
    tipoOcorrencia tipo;
    LocalDateTime instante;
    String descricao;
    Map<String, Object> detalhes;


    public OcorrenciaResponseDTO(Ocorrencia ocorrencia) {

        this.id = ocorrencia.getId();
        this.missaoId = ocorrencia.getMissaoId();
        this.agenteId = ocorrencia.getAgenteId();
        this.agenteNome = ocorrencia.getAgenteNome();
        this.tipo = ocorrencia.getTipo();
        this.instante = ocorrencia.getInstante();
        this.descricao = ocorrencia.getDescricao();
        this.detalhes = ocorrencia.getDetalhes();
    }
}