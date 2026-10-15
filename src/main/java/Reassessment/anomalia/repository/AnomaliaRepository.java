package Reassessment.anomalia.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Reassessment.anomalia.Anomalia;
import Reassessment.enums.nivelRisco;
import Reassessment.enums.statusAnomalia;

public interface AnomaliaRepository extends JpaRepository<Anomalia,Long>{
    
List<Anomalia> findByStatus(statusAnomalia status);

List<Anomalia> findByRisco(nivelRisco nivelRisco);

List<Anomalia> findByStatusIn(statusAnomalia status); // ativas

List<Anomalia> findByStatusInAndRisco(statusAnomalia status, nivelRisco nivelRisco); // criticas

boolean existsByEventoID(Long anomaliaID); // pra evitar deletar um evento com anomalia

Long countByStatusIn(List<statusAnomalia> status); // ver quantidade ativas

Long countByStatusInAndRisco(statusAnomalia status, nivelRisco nivelRisco); // Quantidade de anomalias criticas

// critica e ativa e com risco ativo
}