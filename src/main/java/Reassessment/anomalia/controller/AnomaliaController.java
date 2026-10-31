package Reassessment.anomalia.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
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
import Reassessment.DTO.Anomalia.AlteracaoRiscoRequest;
import Reassessment.DTO.Anomalia.AlteracaoStatusAnomaliaRequest;
import Reassessment.DTO.Anomalia.AnomaliaRequestDTO;
import Reassessment.DTO.Anomalia.AnomaliaResponseDTO;
import Reassessment.DTO.Historico.RegistroHistoricoResponseDTO;
import Reassessment.anomalia.service.AnomaliaService;
import Reassessment.enums.nivelRisco;
import Reassessment.enums.statusAnomalia;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/anomalias")
@RequiredArgsConstructor  // Cria
public class AnomaliaController {
 
    private final AnomaliaService anomaliaService;


    // Listar as anomalia

    @GetMapping 
    public List<AnomaliaResponseDTO> listar(@RequestParam(required = false) statusAnomalia status, @RequestParam(required = false) nivelRisco risco) {

        List<AnomaliaResponseDTO> anomalias = anomaliaService.listar(status, risco);

        return anomalias;
    }

    @GetMapping("/ativas")
    public List<AnomaliaResponseDTO> listarAtivas() {

    List<AnomaliaResponseDTO> anomaliasAtivas = anomaliaService.listarAnomaliasAtivas();

        return anomaliasAtivas;
    }

    @GetMapping("/criticas")
    public List<AnomaliaResponseDTO> listarCriticas() {

    List<AnomaliaResponseDTO> anomaliasCriticas = anomaliaService.listarAnomaliasCriticas();

        return anomaliasCriticas;
    }


    @GetMapping("/{id}/historico")
    public List<RegistroHistoricoResponseDTO> listarHistorico(@PathVariable Long id) {


        List<RegistroHistoricoResponseDTO> historicoAnomalia =  anomaliaService.listarHistorico(id);

        return historicoAnomalia;
    }

    @GetMapping("/{id}")
    public AnomaliaResponseDTO buscarPorId(@PathVariable Long id) {

        AnomaliaResponseDTO anomaliaId = anomaliaService.buscarPorId(id);

        return anomaliaId;
    }

    // ==============

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping 
    public AnomaliaResponseDTO criar(@Valid @RequestBody AnomaliaRequestDTO dto) {

        return anomaliaService.criar(dto);
    }

    // ====================================


    // Alteração 

    @PutMapping ("/{id}")
    public AnomaliaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody AnomaliaRequestDTO dto) {

        AnomaliaResponseDTO anomaliaAlterada = anomaliaService.atualizar(id, dto);

        return anomaliaAlterada;

    }

    @PatchMapping("/{id}/risco")
    public AnomaliaResponseDTO alterarRisco(@PathVariable Long id, @Valid @RequestBody AlteracaoRiscoRequest dto) {

         AnomaliaResponseDTO anomaliaAlterarRisco = anomaliaService.alterarRisco(id, dto);

        return anomaliaAlterarRisco;

    }


    @PatchMapping("/{id}/status")
    public AnomaliaResponseDTO alterarStatus(@PathVariable Long id, @Valid @RequestBody AlteracaoStatusAnomaliaRequest dto) {

        AnomaliaResponseDTO anomaliaAlterarRisco = anomaliaService.alterarStatusAnomalia(id, dto);

        return anomaliaAlterarRisco;

    
    }
}