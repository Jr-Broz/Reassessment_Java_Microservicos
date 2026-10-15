package Reassessment.historico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Reassessment.historico.RegistroHistorico;

public interface HistoricoRepository extends JpaRepository<RegistroHistorico, Long> { 
    

    List<RegistroHistorico> findByAnomaliaIdOrderByInstanteAsc(Long idAnomalia);
}
