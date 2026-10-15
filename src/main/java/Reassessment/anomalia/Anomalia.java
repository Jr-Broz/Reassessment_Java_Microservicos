package Reassessment.anomalia;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import Reassessment.enums.nivelRisco;
import Reassessment.enums.origemAnomalia;
import Reassessment.enums.statusAnomalia;
import Reassessment.evento.EventoHIstorico;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity  
@Getter 
@Setter 
@Table(name = "anomalias")
public class Anomalia {


    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String divergencia;

    @Column(nullable = false)
    @CreationTimestamp()
    private LocalDateTime detectadaEm;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)  
    private nivelRisco risco;

    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)  
    private statusAnomalia status = statusAnomalia.DETECTADA;

    
    @Enumerated(EnumType.STRING) 
    private origemAnomalia origem;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private EventoHIstorico evento;



}