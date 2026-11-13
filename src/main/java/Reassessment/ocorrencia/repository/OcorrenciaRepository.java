package Reassessment.ocorrencia.repository;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import Reassessment.ocorrencia.Ocorrencia;

public interface OcorrenciaRepository extends MongoRepository<Ocorrencia, String> {
    
    // idOcorrencia ou Missao ID??
    List<Ocorrencia> findByMissaoIdOrderByInstanteAsc(Long idMissao); 
}
