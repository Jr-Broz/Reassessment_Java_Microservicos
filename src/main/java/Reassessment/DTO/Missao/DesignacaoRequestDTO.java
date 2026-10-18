package Reassessment.DTO.Missao;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public class DesignacaoRequestDTO {
 
    @NotEmpty 
    List<Long> agentesIds;
}
