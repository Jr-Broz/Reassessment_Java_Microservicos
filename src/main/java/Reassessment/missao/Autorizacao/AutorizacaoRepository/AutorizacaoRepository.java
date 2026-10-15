package Reassessment.missao.Autorizacao.AutorizacaoRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Reassessment.missao.Autorizacao.Autorizacao;

public interface  AutorizacaoRepository extends JpaRepository<Autorizacao, Long>{
    
    Optional<Autorizacao> findByMissaoId(Long missao);

    boolean existsByMissaoId(Long missao);




}
