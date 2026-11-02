package Reassessment.missao.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Reassessment.DTO.Missao.DesignacaoRequestDTO;
import Reassessment.DTO.Missao.MissaoResponseDTO;
import Reassessment.agente.Agente;
import Reassessment.agente.repository.AgenteRepository;
import Reassessment.common.exception.ConflitoRegraNegociosException;
import Reassessment.common.exception.RecursoNaoEncontradoException;
import Reassessment.enums.statusAgente;
import Reassessment.enums.statusMissao;
import Reassessment.enums.tipoRegistro;
import Reassessment.historico.service.HistoricoService;
import Reassessment.missao.Missao;
import Reassessment.missao.repository.MissaoRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class OperacaoMissaoService {
 
    private final  MissaoRepository missaoRepository;
    private final  AgenteRepository agenteRepository;
    private final HistoricoService historicoService;

    private Missao buscarMissao(Long id) {

        return missaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Missao com ID: " + id + "  nao foi achado"));
    }


    @Transactional 
    public MissaoResponseDTO designarAgentes(Long missaoId, DesignacaoRequestDTO dto) {

        Missao missao = buscarMissao(missaoId);

        if(missao.getStatus() != statusMissao.AUTORIZADA && missao.getStatus() != statusMissao.PLANEJADA) {
            throw new ConflitoRegraNegociosException("so as missoes q n começaram pode receber agentes");
        }

        for(Long idAgente : dto.getAgentesIds()) {

            Agente agente = agenteRepository.findById(idAgente).orElseThrow(() -> new RecursoNaoEncontradoException("Agente  com ID: " + idAgente + "  nao foi achado"));            

            if(agente.getStatusDoAgente() != statusAgente.DISPONIVEL)  {
                throw new ConflitoRegraNegociosException("Agente nao esta disponivel");
            }

            if(missao.getAgentes().contains(agente)) {
                throw new ConflitoRegraNegociosException("O agente ja designado pra essa missao ");
            }
            
            missao.getAgentes().add(agente);
            
        }

        historicoService.registrar(missao.getAnomalia().getId(), tipoRegistro.AGENTES_DESIGNADOS, "sistema", "Agentes designados para missao " + missao.getCodigoMissao(), null, dto.getAgentesIds().toString());
        return new MissaoResponseDTO(missao);
    }
}