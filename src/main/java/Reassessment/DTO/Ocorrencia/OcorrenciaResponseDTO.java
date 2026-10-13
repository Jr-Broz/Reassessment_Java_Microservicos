package Reassessment.DTO.Ocorrencia;

import java.time.LocalDateTime;

import Reassessment.enums.tipoOcorrencia;

public class OcorrenciaResponseDTO {
    Long id;
    Long missaoID;
    Long agenteID;
    String agenteNome;
    tipoOcorrencia tipo;
    LocalDateTime instante;
    String descricao;
}
