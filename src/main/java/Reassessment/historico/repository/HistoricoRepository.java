package Reassessment.historico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Reassessment.historico.RegistroHistorico;

public interface HistoricoRepository extends JpaRepository<RegistroHistorico, Long>{
    
}
