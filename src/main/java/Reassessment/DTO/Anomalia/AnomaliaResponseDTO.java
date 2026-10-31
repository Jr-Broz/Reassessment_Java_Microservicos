package Reassessment.DTO.Anomalia;

import java.time.LocalDateTime;

import Reassessment.anomalia.Anomalia;
import Reassessment.enums.nivelRisco;
import Reassessment.enums.origemAnomalia;
import Reassessment.enums.statusAnomalia;
import lombok.Getter;

@Getter 
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




    // Falta ver questões de "evento"
    public AnomaliaResponseDTO(Anomalia anomalia) {

        this.id = anomalia.getId();
        this.eventoId = anomalia.getEvento().getId();
        this.eventoTitulo = anomalia.getEvento().getTitulo();
        this.divergencia = anomalia.getDivergencia();
        this.detectadaEm = anomalia.getDetectadaEm();
        this.risco = anomalia.getRisco();
        this.status = anomalia.getStatus();
        this.origem = anomalia.getOrigem();
        this.observacoes = anomalia.getObservacoes();
    }






}