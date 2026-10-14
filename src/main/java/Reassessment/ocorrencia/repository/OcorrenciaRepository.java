package Reassessment.ocorrencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Reassessment.ocorrencia.Ocorrencia;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia,Long> {
    
}
