package Reassessment.anomalia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Reassessment.anomalia.Anomalia;

public interface AnomaliaRepository extends JpaRepository<Anomalia,Long>{
    
}