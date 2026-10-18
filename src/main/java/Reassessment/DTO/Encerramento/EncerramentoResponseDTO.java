package Reassessment.DTO.Encerramento;

import Reassessment.enums.nivelImpacto;
import Reassessment.enums.statusAnomalia;
import Reassessment.enums.statusMissao;
import Reassessment.missao.Missao;
import Reassessment.missao.Encerramento.EncerramentoMissao;

public class EncerramentoResponseDTO {
 
    
    Long id;
    Missao missaoID; //tava long antes
    statusMissao resultadoMissao;
    String resumo;
    nivelImpacto impactoObservado;
    statusAnomalia situacaoFinalAnomalia;
    String observacoes;


   public EncerramentoResponseDTO(EncerramentoMissao encerramento) {

    this.id = encerramento.getId();
    this.missaoID = encerramento.getMissao();
    this.resultadoMissao = encerramento.getResultadoMissao();
    this.resumo =  encerramento.getResumo();
    this.impactoObservado = encerramento.getImpactoObservado();
    this.situacaoFinalAnomalia = encerramento.getSituacaoFinalANomalia();
    this.observacoes = encerramento.getObservacoes();
   }

}