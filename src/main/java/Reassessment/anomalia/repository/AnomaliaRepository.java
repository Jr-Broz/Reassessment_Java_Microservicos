package Reassessment.anomalia.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Reassessment.anomalia.Anomalia;
import Reassessment.enums.nivelRisco;
import Reassessment.enums.statusAnomalia;

// In existe lista no parametro

public interface AnomaliaRepository extends JpaRepository<Anomalia,Long>{
    
List<Anomalia> findByStatus(statusAnomalia status);

List<Anomalia> findByRisco(nivelRisco risco);

List<Anomalia> findByStatusIn(List<statusAnomalia> status); // ativas

List<Anomalia> findByStatusInAndRisco(List<statusAnomalia> status, nivelRisco nivelRisco); // criticas

boolean existsByEventoId(Long evento); // pra evitar deletar um evento com anomalia

Long countByStatusIn(List<statusAnomalia> status); // ver quantidade ativas

Long countByStatusInAndRisco(List<statusAnomalia> status, nivelRisco nivelRisco); // Quantidade de anomalias criticas

}