package Reassessment.ocorrencia.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import Reassessment.DTO.Ocorrencia.OcorrenciaRequestDTO;
import Reassessment.DTO.Ocorrencia.OcorrenciaResponseDTO;
import Reassessment.agente.Agente;
import Reassessment.agente.repository.AgenteRepository;
import Reassessment.common.exception.ConflitoRegraNegociosException;
import Reassessment.common.exception.DadosInvalidosException;
import Reassessment.common.exception.RecursoNaoEncontradoException;
import Reassessment.enums.statusMissao;
import Reassessment.enums.tipoOcorrencia;
import Reassessment.enums.tipoRegistro;
import Reassessment.historico.service.HistoricoService;
import Reassessment.missao.Missao;
import Reassessment.missao.repository.MissaoRepository;
import Reassessment.ocorrencia.Ocorrencia;
import Reassessment.ocorrencia.repository.OcorrenciaRepository;
import Reassessment.usuario.repository.UsuarioRepository;
import org.springframework.security.access.AccessDeniedException;
import Reassessment.enums.Perfil;
import Reassessment.usuario.Usuario;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor  
public class OcorrenciaService {
    
    private final OcorrenciaRepository ocorrenciaRepository;
    private final MissaoRepository missaoRepository;
    private final AgenteRepository agenteRepository;
    private final HistoricoService historicoService;
    private final UsuarioRepository usuarioRepository;

    @Transactional 
    public OcorrenciaResponseDTO registrar(Long missaoId, OcorrenciaRequestDTO dto, String username) {

     Missao missao = missaoRepository.findById(missaoId).orElseThrow(() -> new RecursoNaoEncontradoException("MIssao com id " + missaoId + " nao foi encontrada"));

        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário " + username + " não encontrado"));

        if (usuario.getPerfil() == Perfil.AGENTE && (usuario.getAgente() == null || !usuario.getAgente().getId().equals(dto.getAgenteId()))) {
            throw new AccessDeniedException("Agente n pode registrar outro nome alem do seu");
        }

     if(missao.getStatus() != statusMissao.EM_EXECUCAO) {
        throw new ConflitoRegraNegociosException("somente as missoes que estao em execucao podem registrar alguma ocorrencia");

     }
     Agente agente = agenteRepository.findById(dto.getAgenteId()).orElseThrow(() -> new RecursoNaoEncontradoException("Agente com id " + dto.getAgenteId() +  " nao foi achado"));

     if(!missao.getAgentes().contains(agente)) {
        throw new ConflitoRegraNegociosException("O agente precisa estar designado na missao");
     }

     Map<String, Object> detalhes = dto.getDetalhes();

     if(detalhes == null) {
        detalhes = new HashMap<>();
        
     }
        if(dto.getTipo() == tipoOcorrencia.DISTORCAO_TEMPORAL && (!detalhes.containsKey("intensidade") || !detalhes.containsKey("duracao"))) {
            throw new DadosInvalidosException("As distorcoes temporais precisam de inteisidade e duracao no detalhes");
        }

     if(dto.getTipo() == tipoOcorrencia.EVIDENCIA_TEMPORAL && (!detalhes.containsKey("objeto") || !detalhes.containsKey("periodoEstimado" ) || !detalhes.containsKey("grauConfiabilidade"))) {
        throw new DadosInvalidosException("evidencia temporal precisa de objeto, periodo estimado e grau de confiabilidade, incluidos no detalhes");
     }

     Ocorrencia ocorrencia = new Ocorrencia();

     ocorrencia.setMissaoId(missao.getId());
     ocorrencia.setAgenteId(agente.getId());
     ocorrencia.setAgenteNome(agente.getNome());
     ocorrencia.setTipo(dto.getTipo());
     ocorrencia.setInstante(LocalDateTime.now());
     ocorrencia.setDescricao(dto.getDescricao());
     ocorrencia.setDetalhes(detalhes);

     historicoService.registrar(missao.getAnomalia().getId(), tipoRegistro.OCORRENCIA_REGISTRADA, username, "ocorrencia registrada na missao " + missao.getCodigoMissao(), null, dto.getTipo().name());
     
     Ocorrencia ocorrenciaSalva = ocorrenciaRepository.save(ocorrencia);
     return new OcorrenciaResponseDTO(ocorrenciaSalva);

    }



    public List<OcorrenciaResponseDTO> listarPorMissao(Long missaoId) {
        
        if(!missaoRepository.existsById(missaoId)) {
            throw new RecursoNaoEncontradoException("Missao com id " + missaoId + " nao foi achado") ;
        }
        List<Ocorrencia> ocorrencias = ocorrenciaRepository.findByMissaoIdOrderByInstanteAsc(missaoId);
        
        List<OcorrenciaResponseDTO> lista = new ArrayList<>();
 
        for (Ocorrencia o : ocorrencias) {
            lista.add(new OcorrenciaResponseDTO(o));

        }
        return lista;
    }

}