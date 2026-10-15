package Reassessment.ocorrencia.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Reassessment.ocorrencia.Ocorrencia;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia,Long> {
    
    // idOcorrencia ou Missao ID??
    List<Ocorrencia> findByMissaoIdORderByInstanteAsc(Long idOcorrencia); 
}
