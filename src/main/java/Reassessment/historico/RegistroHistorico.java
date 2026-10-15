package Reassessment.historico;

import java.time.LocalDateTime;
import Reassessment.enums.tipoRegistro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// tem q ser possivel reconsturi sequencia de acontecimentos da anomalia -at


@Getter 
@Setter 
@NoArgsConstructor 
@Entity
@Table(name="historico_anomalias")
public class RegistroHistorico {
    

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "anomalia_id", nullable = false)
    private Long anomaliaId;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private tipoRegistro  tipo_Registro;
    

    @Column(nullable = false)
    private LocalDateTime instante;

    
    @Column(nullable = false)
    private String usuario;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricaoRegistro;

    

    @Column
    private String valorAnterior; // caso haja mudança, por exm de risco moderado para alto


    @Column
    private String valorNovo;

    @PrePersist
    public void PrePersist() {
        this.instante = LocalDateTime.now();
    }

}