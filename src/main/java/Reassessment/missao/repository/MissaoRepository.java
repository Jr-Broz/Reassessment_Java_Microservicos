package Reassessment.missao.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Reassessment.enums.statusMissao;
import Reassessment.missao.Missao;

public interface MissaoRepository extends JpaRepository<Missao,Long> {

    List<Missao> findByStatus(statusMissao status);
    
    List<Missao> findByStatusEmExecucao(statusMissao status);

    List<Missao> findByAnomaliaID(long missao_id);

    List<Missao> findByAgentesId(Long agentes);

    boolean existsByAgentesId(Long missao_id);

    long countByStatus(statusMissao status);

}