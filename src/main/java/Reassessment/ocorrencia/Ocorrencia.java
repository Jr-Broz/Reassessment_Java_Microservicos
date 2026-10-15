package Reassessment.ocorrencia;
import java.time.LocalDateTime;
import Reassessment.agente.Agente;
import Reassessment.enums.tipoOcorrencia;
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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter 
@Setter 
@NoArgsConstructor 
@Table(name="ocorrencias")
public class Ocorrencia {
 
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;



    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "agente_id", nullable = false)
    private Agente agente;
    



    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private tipoOcorrencia tipo;

    
    @Column(nullable = false)
    private LocalDateTime  instante;



    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

}