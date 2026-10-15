package Reassessment.agente;

import Reassessment.enums.Especialidade;
import Reassessment.enums.statusAgente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter  
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="agentes")
public class Agente {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agenteID; 

    @Column(nullable = false)
    private String nome;
    
    
    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private Especialidade especialidade;

    // voltar aqui e ver isso de enumerated enum type
    @Enumerated(EnumType.STRING)  @Column(nullable = false)
    private statusAgente statusDoAgente = statusAgente.DISPONIVEL;
 

}