package Reassessment.DTO.Painel;

import java.io.Serializable;

import Reassessment.enums.estadoLinhaTemporal;

public record EstadoOperacionalResponseDTO(long agentesDisponiveis, long agentesEmMissao, long missoesEmExecucao, long anomaliasAtivas, long anomaliasCriticas, int indiceEstabilidade, estadoLinhaTemporal estadoLinhaTemporal) implements Serializable{ 
}
