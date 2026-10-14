package Reassessment.missao.Encerramento.EncerramentoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import Reassessment.missao.Encerramento.EncerramentoMissao;

public interface EncerramentoRepository extends JpaRepository<EncerramentoMissao,Long> {
    
}
