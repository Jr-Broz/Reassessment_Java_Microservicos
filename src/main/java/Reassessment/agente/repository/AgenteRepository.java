package Reassessment.agente.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import Reassessment.agente.Agente;
import Reassessment.enums.Especialidade;
import Reassessment.enums.statusAgente;

// derived query

public interface AgenteRepository extends JpaRepository<Agente,Long> {



    List<Agente> findByStatusdoAgente(statusAgente status);

    List<Agente> findByEspecialidade(Especialidade especialidadeAgente);

    boolean existsByMatricula(String matricula);

    Optional<Agente> findByMatricula(String matricula);

    long countByQuantity(statusAgente status);
}