package Reassessment.DTO.Intervencao;

import java.time.LocalDateTime;

import Reassessment.enums.nivelImpacto;

public class IntervensaoResponseDTO {

    Long id;
    Long missaoId;
    Long agendeId;
    String agenteNome;
    LocalDateTime instante;
    String acao;
    String justificativa;
    nivelImpacto impactoEstimado;
    String resultadoObservado;


}