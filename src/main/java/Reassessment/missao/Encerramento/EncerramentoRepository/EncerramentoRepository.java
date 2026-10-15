package Reassessment.missao.Encerramento.EncerramentoRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import Reassessment.missao.Encerramento.EncerramentoMissao;

public interface EncerramentoRepository extends JpaRepository<EncerramentoMissao,Long> {
    

    // Checar  sobre id encerramento missoes
    Optional<EncerramentoMissao> findByMissaoId(Long missao);

    boolean existsByMissaoId(Long missao);

}
