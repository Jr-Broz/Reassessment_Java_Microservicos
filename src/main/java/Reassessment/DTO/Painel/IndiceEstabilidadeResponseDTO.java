package Reassessment.DTO.Painel;

import java.io.Serializable;

import Reassessment.enums.estadoLinhaTemporal;

public record IndiceEstabilidadeResponseDTO(int indice, estadoLinhaTemporal estado, long anomaliasAtivas, long anomaliasCriticas, long missoesEmExecucao, long missoesFalhas, long intervencoesSeveras) implements Serializable{


}
    
