package Reassessment.DTO.Encerramento;

import Reassessment.enums.nivelImpacto;
import Reassessment.enums.statusAnomalia;
import Reassessment.enums.statusMissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EncerramentoRequestDTO {


    @NotNull 
    statusMissao resultadoMissao;

    @NotBlank 
    String resumo;

    @NotNull 
    nivelImpacto impactoObservado;

    @NotNull 
    statusAnomalia situacaoFinalAnomalia;


    String observacoes;

}
