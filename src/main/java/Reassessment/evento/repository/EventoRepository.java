package Reassessment.evento.repository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Reassessment.enums.Importancia;
import Reassessment.evento.EventoHIstorico;

public interface EventoRepository extends JpaRepository<EventoHIstorico, Long> {
    
    List<EventoHIstorico> findByImportancia(Importancia importancia);

    List<EventoHIstorico> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);


}
