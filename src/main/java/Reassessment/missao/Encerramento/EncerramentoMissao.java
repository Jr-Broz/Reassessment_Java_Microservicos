package Reassessment.missao.Encerramento;
import Reassessment.enums.nivelImpacto;
import Reassessment.enums.statusAnomalia;
import Reassessment.enums.statusMissao;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter 
@Setter 
@NoArgsConstructor 
@Table(name="encerramentos_missao")
public class EncerramentoMissao {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "missao_id", nullable = false, unique = true)
    Missao missao;


    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    statusMissao resultadoMissao;

    @Column(nullable = false, columnDefinition = "TEXT")
    String resumo;

    @Column(nullable = false, columnDefinition = "TEXT")
    nivelImpacto impactoObservado;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    statusAnomalia situacaoFinalANomalia;

    @Column(columnDefinition = "TEXT")
    String observacoes;
}