package Reassessment.intervencao.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import Reassessment.enums.nivelImpacto;
import Reassessment.intervencao.Intervencao;

public interface IntervencaoRepository extends JpaRepository<Intervencao, Long> {
    

    List<Intervencao> findByMissaoIdOrderByInstanteAsc(Long idMissao);

    long countByImpactoEstimado(nivelImpacto impactoEstimado);

}