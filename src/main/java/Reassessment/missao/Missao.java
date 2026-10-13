package Reassessment.missao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Reassessment.agente.Agente;
import Reassessment.anomalia.Anomalia;
import Reassessment.enums.Prioridade;
import Reassessment.enums.statusMissao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter 
@Setter 
@NoArgsConstructor 
@Table(name="missoes")
public class Missao {
    
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missao_id;



    @Column(nullable = false, unique = true)
    private String codigoMissao;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String objetivo;


    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "anomalia_id", nullable = false)
    private Anomalia anomalia;


    @Column(nullable = false)
    private LocalDate  destinoData; 


    @Column(nullable = false)
    private String destinoLocal;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Prioridade prioridade;


    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private statusMissao status = statusMissao.PLANEJADA;

    @ManyToMany 
    @JoinTable 
    private List<Agente> agentes = new ArrayList<>();

}