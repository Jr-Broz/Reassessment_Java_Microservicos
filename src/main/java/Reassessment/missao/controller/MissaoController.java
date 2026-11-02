package Reassessment.missao.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import Reassessment.DTO.Missao.DesignacaoRequestDTO;
import Reassessment.DTO.Missao.MissaoRequestDTO;
import Reassessment.DTO.Missao.MissaoResponseDTO;
import Reassessment.enums.statusMissao;
import Reassessment.missao.service.MissaoService;
import Reassessment.missao.service.OperacaoMissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/missoes")
@RequiredArgsConstructor 
public class MissaoController {
 
    private final MissaoService missaoService;
    private final OperacaoMissaoService operacaoMissaoService;

    @GetMapping 
    public List<MissaoResponseDTO> listar(@RequestParam(required = false) statusMissao status) {
        
        return missaoService.listar(status);
    }

    @GetMapping("/em-execucao") 
    public List<MissaoResponseDTO> listarEmExecucao() {

        return missaoService.listarEmExecucao();
    }

    @GetMapping("/{id}")
    public MissaoResponseDTO buscarPorId(@PathVariable Long id) {

        return missaoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MissaoResponseDTO criar(@Valid @RequestBody MissaoRequestDTO dto) {

        return missaoService.criar(dto);
    }

    @PutMapping("/{id}")
    public MissaoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody MissaoRequestDTO dto){

        return missaoService.atualizar(id, dto);
    }


    @PostMapping("/{id}/agentes")
    public MissaoResponseDTO designarAgentes(@PathVariable Long id, @Valid @RequestBody DesignacaoRequestDTO dto) {
        return operacaoMissaoService.designarAgentes(id, dto);
    }
}