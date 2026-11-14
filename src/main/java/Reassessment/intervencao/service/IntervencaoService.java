package Reassessment.intervencao.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Reassessment.DTO.Intervencao.IntervencaoRequestDTO;
import Reassessment.DTO.Intervencao.IntervencaoResponseDTO;
import Reassessment.agente.Agente;
import Reassessment.agente.repository.AgenteRepository;
import Reassessment.common.exception.ConflitoRegraNegociosException;
import Reassessment.common.exception.RecursoNaoEncontradoException;
import Reassessment.enums.Perfil;
import Reassessment.enums.statusMissao;
import Reassessment.enums.tipoRegistro;
import Reassessment.historico.service.HistoricoService;
import Reassessment.intervencao.Intervencao;
import Reassessment.intervencao.repository.IntervencaoRepository;
import Reassessment.missao.Missao;
import Reassessment.missao.repository.MissaoRepository;
import Reassessment.usuario.Usuario;
import Reassessment.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
@Service 
@RequiredArgsConstructor 
public class IntervencaoService {
    
    private final IntervencaoRepository intervencaoRepository;
    private final MissaoRepository missaoRepository;
    private final AgenteRepository agenteRepository;
    private final HistoricoService historicoService;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public IntervencaoResponseDTO registrar(Long missaoId, IntervencaoRequestDTO dto, String username) {

        Missao missao = missaoRepository.findById(missaoId).orElseThrow(() -> new RecursoNaoEncontradoException("Missão com ID " + missaoId + " não encontrada"));
    
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new RecursoNaoEncontradoException("Usuario" + username + " nao foi encontrado"));
        if(usuario.getPerfil() == Perfil.AGENTE && (usuario.getAgente() == null  || !usuario.getAgente().getId().equals(dto.getAgenteId()))){
            throw new org.springframework.security.access.AccessDeniedException("Agente n pode registrar outro nome alem do seu");
        }        

        if(missao.getStatus() != statusMissao.EM_EXECUCAO) {
            throw new ConflitoRegraNegociosException("So as missoes em execucao podem ser intervidas");
        }

        Agente agente = agenteRepository.findById(dto.getAgenteId()).orElseThrow(() -> new RecursoNaoEncontradoException("Agente com Id" + dto.getAgenteId() + " nao foi encontrado"));
        
        if(!missao.getAgentes().contains(agente)) {
            throw new ConflitoRegraNegociosException("agentes precisam estar designados na missao");
        }
   
        Intervencao intervencao = new Intervencao();

        intervencao.setMissao(missao);
        intervencao.setAgente(agente);
        intervencao.setAcao(dto.getAcao());
        intervencao.setJustificativa(dto.getJustificativa());
        intervencao.setImpactoEstimado(dto.getImpactoEstimado());
        intervencao.setResultadoObservado(dto.getResultadoObservado());

        Intervencao intervencaoSalva = intervencaoRepository.save(intervencao);
        historicoService.registrar(missao.getAnomalia().getId(), tipoRegistro.INTERVENCAO_REGISTRADA, username, "Intervencao na missao " + missao.getCodigoMissao(), null, dto.getImpactoEstimado().name());
        
        return new IntervencaoResponseDTO(intervencaoSalva);
    }

   @Transactional(readOnly = true)
    public List<IntervencaoResponseDTO> listarPorMissao(Long missaoId) {
        
        if(!missaoRepository.existsById(missaoId)) {
            throw new RecursoNaoEncontradoException("Missao com id  " + missaoId + " nao foi encontrada");

        }
        List<Intervencao> intervencoes = intervencaoRepository.findByMissaoIdOrderByInstanteAsc(missaoId);

        List<IntervencaoResponseDTO> lista = new ArrayList<>();

        for (Intervencao intervencao : intervencoes) {
            lista.add(new IntervencaoResponseDTO(intervencao));
        }

        return lista;
    }










}