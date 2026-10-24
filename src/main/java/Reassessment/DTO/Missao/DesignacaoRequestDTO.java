package Reassessment.DTO.Missao;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data 
public class DesignacaoRequestDTO {
 
    @NotEmpty 
    List<Long> agentesIds;
}
