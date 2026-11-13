package Reassessment.missao.service;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Reassessment.DTO.Autorizacao.AutorizacaoRequestDTO;
import Reassessment.DTO.Autorizacao.AutorizacaoResponseDTO;
import Reassessment.DTO.Missao.DesignacaoRequestDTO;
import Reassessment.DTO.Missao.MissaoResponseDTO;
import Reassessment.agente.Agente;
import Reassessment.agente.repository.AgenteRepository;
import Reassessment.anomalia.Anomalia;
import Reassessment.anomalia.service.AnomaliaService;
import Reassessment.common.exception.ConflitoRegraNegociosException;
import Reassessment.common.exception.RecursoNaoEncontradoException;
import Reassessment.enums.Prioridade;
import Reassessment.enums.nivelRisco;
import Reassessment.enums.statusAgente;
import Reassessment.enums.statusAnomalia;
import Reassessment.enums.statusMissao;
import Reassessment.enums.tipoMissao;
import Reassessment.enums.tipoRegistro;
import Reassessment.historico.service.HistoricoService;
import Reassessment.missao.Missao;
import Reassessment.missao.Autorizacao.Autorizacao;
import Reassessment.missao.Autorizacao.AutorizacaoRepository.AutorizacaoRepository;
import Reassessment.missao.Encerramento.EncerramentoRepository.EncerramentoRepository;
import Reassessment.missao.repository.MissaoRepository;
import Reassessment.usuario.Usuario;
import Reassessment.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import Reassessment.DTO.Encerramento.EncerramentoRequestDTO;
import Reassessment.DTO.Encerramento.EncerramentoResponseDTO;
import Reassessment.missao.Encerramento.EncerramentoMissao;
import Reassessment.enums.nivelImpacto;
import Reassessment.common.exception.DadosInvalidosException;


@Service 
@RequiredArgsConstructor 
public class OperacaoMissaoService {
 
    private final MissaoRepository missaoRepository;
    private final AgenteRepository agenteRepository;
    private final HistoricoService historicoService;
    private final AutorizacaoRepository autorizacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EncerramentoRepository encerramentoRepository;

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

    // -----------------

    @Transactional 
    public AutorizacaoResponseDTO autorizar(Long missaoId, AutorizacaoRequestDTO dto, String username) {

        Missao missao = buscarMissao(missaoId);

        if(missao.getStatus() != statusMissao.PLANEJADA) {
            throw new ConflitoRegraNegociosException("So missao planejada tem como ser autorizada");
        }

        Usuario responsavel = usuarioRepository.findByUsername(username).orElseThrow(() -> new RecursoNaoEncontradoException("Usuario com nome " + username + " Nao foi encotnrad"));

        Autorizacao autorizacao = new Autorizacao();

        autorizacao.setMissao(missao);
        autorizacao.setResponsavel(responsavel);
        autorizacao.setInstante(LocalDateTime.now());
        autorizacao.setObservacao(dto.getObservacao());
        Autorizacao salva = autorizacaoRepository.save(autorizacao);

        missao.setStatus(statusMissao.AUTORIZADA);
        historicoService.registrar(missao.getAnomalia().getId(), tipoRegistro.MISSAO_AUTORIZADA, username, "Missao " + missao.getCodigoMissao() + " Autorizada", "PLANEJADA", "AUTORIZADA");
        return new AutorizacaoResponseDTO(salva);
    }


    @Transactional 
    public MissaoResponseDTO iniciar(Long missaoId, String username) {

        Missao missao = buscarMissao(missaoId);

        Anomalia anomalia = missao.getAnomalia();

        if(missao.getStatus() != statusMissao.PLANEJADA && missao.getStatus() != statusMissao.AUTORIZADA) {
            throw new ConflitoRegraNegociosException("Missao n pode ser iniciada no estado atual");
        }

        if(missao.getPrioridade() == Prioridade.EMERGENCIA && missao.getStatus() != statusMissao.AUTORIZADA) {
            throw new ConflitoRegraNegociosException("Missao de emergencia precisa de autorizacao primeiro");
        }

        if(anomalia.getRisco() == nivelRisco.CRITICO && missao.getStatus() != statusMissao.AUTORIZADA) {
            throw new ConflitoRegraNegociosException("Missao critica precisa ser autorizada antes de comecar");
        }

        if(missao.getAgentes().isEmpty()) {
            throw new ConflitoRegraNegociosException("Missao precisa de pelo menos 1 agente pra poder comecar ");
        }

        if(!AnomaliaService.STATUS_ATIVOS.contains(anomalia.getStatus())) {
            throw new ConflitoRegraNegociosException("ANOMALIA NAO TA MAIS ATIVA");
        }

        for(Agente agente : missao.getAgentes()) {
            if(agente.getStatusDoAgente() != statusAgente.DISPONIVEL) {
                throw new ConflitoRegraNegociosException("agente " + agente.getNome() + " Nao ta disponivel");

            }
            agente.setStatusDoAgente(statusAgente.EM_MISSAO);

        }

        String statusAnterior = missao.getStatus().name();            
        missao.setStatus(statusMissao.EM_EXECUCAO);
        historicoService.registrar(anomalia.getId(), tipoRegistro.MISSAO_INICIADA, username, "Missao " + missao.getCodigoMissao() + " iniciada", statusAnterior, "EM_EXECUCAO");

        if(missao.getTipo() == tipoMissao.CORRECAO && anomalia.getStatus() != statusAnomalia.EM_CORRECAO) {
            String anomaliaAnterior = anomalia.getStatus().name();
            anomalia.setStatus(statusAnomalia.EM_CORRECAO);
            historicoService.registrar(anomalia.getId(), tipoRegistro.STATUS_ALTERADO, username, "Anomalia em correcao pela missao " +  missao.getCodigoMissao(), anomaliaAnterior, "EM_CORRECAO");
        }

        return new MissaoResponseDTO(missao);
    }

    @Transactional     
    public EncerramentoResponseDTO encerrar(Long missaoId, EncerramentoRequestDTO dto, String username) {

        Missao missao = buscarMissao(missaoId);
        Anomalia anomalia = missao.getAnomalia();

        if(missao.getStatus() != statusMissao.EM_EXECUCAO) {
            throw new ConflitoRegraNegociosException("So as missoes EM EXECUCAO podem ser encerradas");

        }

        if(dto.getResultadoMissao() != statusMissao.CONCLUIDA && dto.getResultadoMissao() != statusMissao.FALHOU) {
            throw new DadosInvalidosException("A missao deve ser ou Concluida ou Falhou");
        }

        if(dto.getSituacaoFinalAnomalia() == statusAnomalia.DETECTADA || dto.getSituacaoFinalAnomalia() == statusAnomalia.EM_CORRECAO) {

            throw new DadosInvalidosException("A situacao pra anomalia esta invalida");
        }

        if(dto.getResultadoMissao() == statusMissao.FALHOU && dto.getSituacaoFinalAnomalia() == statusAnomalia.ESTABILIZADA) {
            throw new DadosInvalidosException("Missao que falhou nao pode estabilizar a anomalia");
        }

        // aq e registro do cenrramentoi
        
        EncerramentoMissao encerramento = new EncerramentoMissao();
        encerramento.setMissao(missao);
        encerramento.setResultadoMissao(dto.getResultadoMissao());
        encerramento.setResumo(dto.getResumo());
        encerramento.setSituacaoFinalAnomalia(dto.getSituacaoFinalAnomalia());
        encerramento.setImpactoObservado(dto.getImpactoObservado());
        encerramento.setObservacoes(dto.getObservacoes());
        EncerramentoMissao salvo = encerramentoRepository.save(encerramento);

        missao.setStatus(dto.getResultadoMissao());
        historicoService.registrar(anomalia.getId(), tipoRegistro.MISSAO_ENCERRADA, username, "Missao " + missao.getCodigoMissao() + " encerrada ", "EM_EXECUCAO", dto.getResultadoMissao().name());

        for(Agente agente : missao.getAgentes()) {

            if(dto.getResultadoMissao() == statusMissao.FALHOU && dto.getImpactoObservado() == nivelImpacto.SEVERO) {
                agente.setStatusDoAgente(statusAgente.SUSPENSO);
            }
            else {
                agente.setStatusDoAgente(statusAgente.DISPONIVEL);
            }
        }

        if(AnomaliaService.STATUS_ATIVOS.contains(anomalia.getStatus()) && anomalia.getStatus() != dto.getSituacaoFinalAnomalia()) {
            String anomaliaAnteriuor = anomalia.getStatus().name();
            anomalia.setStatus(dto.getSituacaoFinalAnomalia());
            historicoService.registrar(anomalia.getId(), tipoRegistro.STATUS_ALTERADO, username, "Situacao final da missao " + missao.getCodigoMissao(), anomaliaAnteriuor, dto.getSituacaoFinalAnomalia().name());
        }

        return new EncerramentoResponseDTO(salvo);

    }

    @Transactional 
    public MissaoResponseDTO cancelar(Long missaoId, String username) {
        Missao missao = buscarMissao(missaoId);
        if(missao.getStatus() != statusMissao.PLANEJADA && missao.getStatus() != statusMissao.AUTORIZADA) {
            throw new ConflitoRegraNegociosException("So as missoes que não começaram podem ser canceladas");
        }

        String statusAnterior = missao.getStatus().name();
        missao.setStatus(statusMissao.CANCELADA);
        historicoService.registrar(missao.getAnomalia().getId(), tipoRegistro.MISSAO_CANCELADA, username, "Missao " + missao.getCodigoMissao() + " Cancelada", statusAnterior, "CANCELADA");
        return new MissaoResponseDTO(missao);

    }







}