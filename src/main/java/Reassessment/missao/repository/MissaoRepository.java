package Reassessment.missao.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Reassessment.enums.statusMissao;
import Reassessment.missao.Missao;

public interface MissaoRepository extends JpaRepository<Missao,Long> {

    List<Missao> findByStatus(statusMissao status);
    
    List<Missao> findByAnomaliaId(Long anomaliaId);

    List<Missao> findByAgentesId(Long agentes);

    boolean existsByAgentesId(Long agente_Id);

    long countByStatus(statusMissao status);

}