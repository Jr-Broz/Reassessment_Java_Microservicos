package Reassessment.intervencao.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import Reassessment.DTO.Intervencao.IntervencaoRequestDTO;
import Reassessment.DTO.Intervencao.IntervencaoResponseDTO;
import Reassessment.intervencao.service.IntervencaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/missoes/{missaoId}/intervencoes") 
@RequiredArgsConstructor 
public class IntervencaoController {
    
    private final IntervencaoService intervencaoService;


   
    @PostMapping 
    @ResponseStatus(HttpStatus.CREATED)
    public IntervencaoResponseDTO registrar(@PathVariable  Long missaoId, @Valid @RequestBody IntervencaoRequestDTO dto, Authentication authentication) {

        return intervencaoService.registrar(missaoId, dto, authentication.getName());
    }

    @GetMapping
    public List<IntervencaoResponseDTO> listar(@PathVariable Long missaoId) {
        return intervencaoService.listarPorMissao(missaoId);


        
    }











}
