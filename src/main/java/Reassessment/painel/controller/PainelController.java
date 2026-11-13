package Reassessment.painel.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Reassessment.DTO.Painel.EstadoOperacionalResponseDTO;
import Reassessment.DTO.Painel.IndiceEstabilidadeResponseDTO;
import Reassessment.painel.service.PainelService;
import lombok.RequiredArgsConstructor;



@RestController  
@RequestMapping("/painel")
@RequiredArgsConstructor
public class PainelController {

    private final PainelService painelService;

    @GetMapping("/estado")
    public EstadoOperacionalResponseDTO estado(){
        return painelService.estadoOperacional();
    }

    @GetMapping("/indice")
    public IndiceEstabilidadeResponseDTO indice() {
        return painelService.indiceEstabilidade();
        
    }













}
