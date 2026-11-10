package Reassessment.DTO.Autorizacao;

import java.time.LocalDateTime;

import Reassessment.missao.Autorizacao.Autorizacao;
import lombok.Data;

@Data 
public class AutorizacaoResponseDTO {
    
    Long id;
    Long missaoId;
    String responsavelUsername;
    LocalDateTime instante;
    String observacao;

    public AutorizacaoResponseDTO(Autorizacao autorizacao){

        this.id = autorizacao.getId();
        this.missaoId = autorizacao.getMissao().getId();
        this.responsavelUsername = autorizacao.getResponsavel().getUsername();
        this.instante = autorizacao.getInstante();
        this.observacao = autorizacao.getObservacao();


    }
}
