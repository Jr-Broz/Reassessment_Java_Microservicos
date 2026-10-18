package Reassessment.DTO.Ocorrencia;

import java.time.LocalDateTime;

import Reassessment.agente.Agente;
import Reassessment.enums.tipoOcorrencia;
import Reassessment.missao.Missao;
import Reassessment.ocorrencia.Ocorrencia;

public class OcorrenciaResponseDTO {
    Long id;
    Missao missaoID;
    Agente agenteID;
    // String agenteNome;
    tipoOcorrencia tipo;
    LocalDateTime instante;
    String descricao;


    public OcorrenciaResponseDTO(Ocorrencia ocorrencia) {

        this.id = ocorrencia.getId();
        this.missaoID = ocorrencia.getMissao();
        this.agenteID = ocorrencia.getAgente();
        this.tipo = ocorrencia.getTipo();
        this.instante = ocorrencia.getInstante();
        this.descricao = ocorrencia.getDescricao();
    }




}
