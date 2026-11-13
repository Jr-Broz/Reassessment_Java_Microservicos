package Reassessment.ocorrencia.controller;
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
import Reassessment.DTO.Ocorrencia.OcorrenciaRequestDTO;
import Reassessment.DTO.Ocorrencia.OcorrenciaResponseDTO;
import Reassessment.ocorrencia.service.OcorrenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/missoes/{missaoId}/ocorrencias")
@RequiredArgsConstructor 
public class OcorrenciaController {
    
    private final OcorrenciaService ocorrenciaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OcorrenciaResponseDTO registrar(@PathVariable Long missaoId, @Valid @RequestBody OcorrenciaRequestDTO dto, Authentication authentication) {

        return ocorrenciaService.registrar(missaoId, dto, authentication.getName());
    }

    @GetMapping 
    public List<OcorrenciaResponseDTO> listar(@PathVariable Long missaoId) {
        
        return ocorrenciaService.listarPorMissao(missaoId);
    }

}