package Reassessment.DTO.Encerramento;

import Reassessment.enums.nivelImpacto;
import Reassessment.enums.statusAnomalia;
import Reassessment.enums.statusMissao;
import Reassessment.missao.Encerramento.EncerramentoMissao;
import lombok.Getter;

@Getter 
public class EncerramentoResponseDTO {
 
    
    Long id;
    Long missaoID; 
    statusMissao resultadoMissao;
    String resumo;
    nivelImpacto impactoObservado;
    statusAnomalia situacaoFinalAnomalia;
    String observacoes;


   public EncerramentoResponseDTO(EncerramentoMissao encerramento) {

    this.id = encerramento.getId();
    this.missaoID = encerramento.getMissao().getId();
    this.resultadoMissao = encerramento.getResultadoMissao();
    this.resumo =  encerramento.getResumo();
    this.impactoObservado = encerramento.getImpactoObservado();
    this.situacaoFinalAnomalia = encerramento.getSituacaoFinalAnomalia();
    this.observacoes = encerramento.getObservacoes();
   }

}