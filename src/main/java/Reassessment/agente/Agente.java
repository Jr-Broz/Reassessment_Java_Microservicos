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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter 
@NoArgsConstructor()
@Table(name="agentes")
public class Agente {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private String nome;
    
    
    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) //sem isso o banco guarda posicao
    private Especialidade especialidade;

    // voltar aqui e ver isso de enumerated enum type
    @Enumerated(EnumType.STRING)  
    @Column(nullable = false)
    private statusAgente statusDoAgente = statusAgente.DISPONIVEL;
 

}