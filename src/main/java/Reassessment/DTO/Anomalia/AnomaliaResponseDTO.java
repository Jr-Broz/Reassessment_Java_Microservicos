package Reassessment.DTO.Anomalia;

import java.time.LocalDateTime;

import Reassessment.enums.nivelRisco;
import Reassessment.enums.origemAnomalia;
import Reassessment.enums.statusAnomalia;

public class AnomaliaResponseDTO {
    
    Long id;
    Long eventoId;
    String eventoTitulo;
    String divergencia;
    LocalDateTime detectadaEm;
    nivelRisco risco;
    statusAnomalia status;
    origemAnomalia origem;
    String observacoes;
}