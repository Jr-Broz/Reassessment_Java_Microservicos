package Reassessment.intervencao.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Reassessment.intervencao.Intervencao;

public interface IntervencaoRepository extends JpaRepository<Intervencao,Long> {
    
    // long idIntervencao ou idMissao (missao) ?
    List<Intervencao> findByMissaoIdOrderByInstanteAsc(Long idMissao);

    long countByImpactoEstimado(Intervencao impactoEstimado);

}