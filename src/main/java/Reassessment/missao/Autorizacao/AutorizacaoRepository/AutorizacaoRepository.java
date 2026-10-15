package Reassessment.missao.Autorizacao.AutorizacaoRepository;

import java.util.Optional;

import Reassessment.missao.Autorizacao.Autorizacao;

public interface AutorizacaoRepository {
    
    Optional<Autorizacao> findByMissaoID(Long missao);

    boolean existsByMissaoID(Long missao);














}
