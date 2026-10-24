package Reassessment.evento.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import Reassessment.DTO.Evento.EventoRequestDTO;
import Reassessment.DTO.Evento.EventoResponseDTO;
import Reassessment.enums.Importancia;
import Reassessment.evento.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/eventos")
@RequiredArgsConstructor 
public class EventoController {

private final EventoService eventoService;


// precisei botaro ISO.DATE pq requisicao tava falhando 


@GetMapping  
public List<EventoResponseDTO> listar(@RequestParam(required = false) Importancia importancia , @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio) {

    List<EventoResponseDTO> eventos = eventoService.listarTodosEventos(importancia, null, null);

    return eventos;

}

@GetMapping("/{id}")
public EventoResponseDTO buscarPorId(@PathVariable Long id) {

    EventoResponseDTO eventoId = eventoService.buscarPorId(id);

    return eventoId;
}


@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public EventoResponseDTO criar(@Valid @RequestBody EventoRequestDTO dto) {

    return eventoService.criar(dto);
}

@PutMapping("/{id}")
public EventoResponseDTO atuaalizar(@PathVariable Long id, @Valid @RequestBody EventoRequestDTO dto) {

    EventoResponseDTO atualizarCampo = eventoService.atualizarEvento(id, dto);

    return atualizarCampo;
}

@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void excluirEvento(@PathVariable Long id) {

    eventoService.excluirEvento(null);
}

    
}