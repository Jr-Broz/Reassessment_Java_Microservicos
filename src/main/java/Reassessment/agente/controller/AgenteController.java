package Reassessment.agente.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import Reassessment.DTO.Agente.AgenteRequestDTO;
import Reassessment.DTO.Agente.AgenteResponseDTO;
import Reassessment.DTO.Agente.AlteracaoStatusAgenteRequest;
import Reassessment.agente.service.AgenteService;
import Reassessment.enums.Especialidade;
import Reassessment.enums.statusAgente;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/agentes")
@RequiredArgsConstructor 
public class AgenteController {
    

private final AgenteService agenteService;


@GetMapping 
public List<AgenteResponseDTO> listar(@RequestParam(required = false) statusAgente status, @RequestParam(required = false) Especialidade especialidade) {
 
    
    List<AgenteResponseDTO> agentes = agenteService.listar(status, especialidade);

    return agentes;
}


@GetMapping("/{id}")
public AgenteResponseDTO buscarPorId(@PathVariable Long id) {

    AgenteResponseDTO agenteId = agenteService.buscarPorId(id);

    return agenteId;
}


@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public AgenteResponseDTO criarAgente(@Valid @RequestBody AgenteRequestDTO dto) {

    return agenteService.criar(dto);

}


@PutMapping("/{id}")
public AgenteResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody AgenteRequestDTO dto) {

    AgenteResponseDTO  atualizarValor = agenteService.atualizar(id, dto);

    return atualizarValor;
}


@PatchMapping("/{id}/status")
public AgenteResponseDTO alterarStatusAgente(@PathVariable Long id, @Valid @RequestBody AlteracaoStatusAgenteRequest dto) {

    AgenteResponseDTO agenteAlterado = agenteService.alterarStatus(id, dto);

    return agenteAlterado;
}


@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void excluirAgente(@PathVariable Long id) {

    
    agenteService.excluirAgente(id);
}
}