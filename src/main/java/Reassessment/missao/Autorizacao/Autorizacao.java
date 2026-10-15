package Reassessment.missao.Autorizacao;

import java.time.LocalDateTime;

import Reassessment.missao.Missao;
import Reassessment.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter 
@Setter 
@NoArgsConstructor 
@Entity
@Table(name="autorizacoes")
public class Autorizacao {
 

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id", nullable = false, unique = true)
    private Missao missao;


    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "responsavel_id", nullable = false)
    private Usuario responsavel;


    @Column(nullable = false)
    private LocalDateTime instante;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String observacao;

}