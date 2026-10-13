package Reassessment.usuario;

import Reassessment.agente.Agente;
import Reassessment.enums.Perfil;
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


@Entity
@Table(name="usuarios")
public class Usuario {
 
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuario_id;
    
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private Perfil perfil;

    @OneToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "agente_id", unique = true)
    private Agente agente;

}