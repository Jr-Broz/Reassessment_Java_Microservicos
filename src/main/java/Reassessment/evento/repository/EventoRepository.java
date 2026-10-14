package Reassessment.evento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Reassessment.evento.EventoHIstorico;

public interface EventoRepository extends JpaRepository<EventoHIstorico, Long> {
    
}
