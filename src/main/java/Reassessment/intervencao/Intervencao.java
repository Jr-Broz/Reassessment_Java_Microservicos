package Reassessment.intervencao;
import java.time.LocalDateTime;
import Reassessment.agente.Agente;
import Reassessment.enums.nivelImpacto;
import Reassessment.missao.Missao;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@Entity
@Table(name="intervencoes")
public class Intervencao {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id", nullable = false) //tava idMissao
    private Missao missao;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "agente_id", nullable = false)
    private Agente agente;


    @Column(nullable = false)
    private LocalDateTime instante;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String acao;

    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String justificativa;

    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private nivelImpacto impactoEstimado;


    @Column(columnDefinition = "TEXT")
    private String resultadoObservado;

    @PrePersist 
    public void PrePersist() {
        this.instante = LocalDateTime.now();
    }

}