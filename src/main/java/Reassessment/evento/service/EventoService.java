package Reassessment.evento.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Reassessment.DTO.Evento.EventoRequestDTO;
import Reassessment.DTO.Evento.EventoResponseDTO;
import Reassessment.anomalia.repository.AnomaliaRepository;
import Reassessment.common.exception.ConflitoRegraNegociosException;
import Reassessment.common.exception.DadosInvalidosException;
import Reassessment.common.exception.RecursoNaoEncontradoException;
import Reassessment.enums.Importancia;
import Reassessment.evento.EventoHIstorico;
import Reassessment.evento.repository.EventoRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class EventoService {
    
private final EventoRepository eventoRepository;
private final AnomaliaRepository anomaliaRepository;

   private EventoHIstorico buscarEntidade(Long id) {

        return eventoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Evento com ID: " + id + "nao foi achado"));
    }


    @Transactional 
    public EventoResponseDTO criar(EventoRequestDTO eventoRequestDTO) {

        EventoHIstorico eventoHIstorico = new EventoHIstorico();

        eventoHIstorico.setTitulo(eventoRequestDTO.getTitulo());
        eventoHIstorico.setDescricao(eventoRequestDTO.getDescricao());
        eventoHIstorico.setData(eventoRequestDTO.getData());
        eventoHIstorico.setLocalizacao(eventoRequestDTO.getLocalizacao());
        eventoHIstorico.setImportancia(eventoRequestDTO.getImportancia());

        EventoHIstorico novoEventoSalvo = eventoRepository.save(eventoHIstorico);

        return new EventoResponseDTO(novoEventoSalvo);

    }

    @Transactional(readOnly = true)
public List<EventoResponseDTO> listarTodosEventos(Importancia importancia, LocalDate inicio,LocalDate fim) {
 

    List<EventoHIstorico> eventos;

    if(((inicio != null && fim == null) || (inicio == null && fim != null))) {
        throw new DadosInvalidosException("Precisa inserir as 2 datas");

    }

    if(inicio != null && fim != null) {
        
        if(inicio.isAfter(fim)) {
            
            throw new DadosInvalidosException("Data de inicio n pode ser depois da data do fim");

        }
    
    }


    if(importancia != null) {

        eventos = eventoRepository.findByImportancia(importancia);
    }

    else if (inicio != null && fim != null) {

        eventos = eventoRepository.findByDataBetween(inicio, fim);
    }

    else {
        eventos = eventoRepository.findAll();
    }

    
    ArrayList<EventoResponseDTO> lista = new ArrayList<>();

    for (EventoHIstorico e: eventos) {
        lista.add(new EventoResponseDTO(e));
    }
    return lista;
}



@Transactional(readOnly = true)
public EventoResponseDTO buscarPorId(Long id) {

    EventoHIstorico eventoId =  buscarEntidade(id);

    return new EventoResponseDTO(eventoId);
}


@Transactional 
public EventoResponseDTO atualizarEvento(Long id, EventoRequestDTO dto) {

    EventoHIstorico evento = buscarEntidade(id);

    evento.setTitulo(dto.getTitulo());
    evento.setDescricao(dto.getDescricao());
    evento.setData(dto.getData());
    evento.setLocalizacao(dto.getLocalizacao());
    evento.setImportancia(dto.getImportancia());
    
    return new EventoResponseDTO(evento);

}


// toda anomalia precisa estar ligada a um evento. Apagar o evento deixaria anomalias “órfãs”.

@Transactional 
public void excluirEvento(Long id) {

    buscarEntidade(id); // ver se vai dar erro de não encontrado

    if(anomaliaRepository.existsByEventoId(id)) {
        throw new ConflitoRegraNegociosException("Esse evento em especifico tem uma anomalia atrelada, não pode ser deletado.");
    }

    eventoRepository.deleteById(id);
}
}