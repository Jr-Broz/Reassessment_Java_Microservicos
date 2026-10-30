package Reassessment.anomalia.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Reassessment.DTO.Anomalia.AlteracaoRiscoRequest;
import Reassessment.DTO.Anomalia.AlteracaoStatusAnomaliaRequest;
import Reassessment.DTO.Anomalia.AnomaliaRequestDTO;
import Reassessment.DTO.Anomalia.AnomaliaResponseDTO;
import Reassessment.DTO.Historico.RegistroHistoricoResponseDTO;
import Reassessment.anomalia.Anomalia;
import Reassessment.anomalia.repository.AnomaliaRepository;
import Reassessment.common.exception.ConflitoRegraNegociosException;
import Reassessment.common.exception.DadosInvalidosException;
import Reassessment.common.exception.RecursoNaoEncontradoException;
import Reassessment.enums.nivelRisco;
import Reassessment.enums.statusAnomalia;
import Reassessment.enums.tipoRegistro;
import Reassessment.evento.EventoHIstorico;
import Reassessment.evento.repository.EventoRepository;
import Reassessment.historico.service.HistoricoService;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AnomaliaService {
 
    private final AnomaliaRepository anomaliaRepository;
    private final EventoRepository eventoRepository;
    private final HistoricoService historicoService; // grava no historico a anom


    // https://pt.stackoverflow.com/questions/159342/o-que-%C3%A9-uma-condi%C3%A7%C3%A3o-de-corrida


    public static final List<statusAnomalia> STATUS_ATIVOS = List.of(statusAnomalia.DETECTADA, statusAnomalia.EM_ANALISE, statusAnomalia.CONFIRMADA, statusAnomalia.EM_CORRECAO);

    private Anomalia buscarEntidade(Long id) {

        return anomaliaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Anomalia com ID: " + id + "nao foi achado"));
    }

    // -----------------------------------

    @Transactional(readOnly = true)
    public AnomaliaResponseDTO buscarPorId(Long id) {

        Anomalia anomalia = buscarEntidade(id);
        return new AnomaliaResponseDTO(anomalia);
    }

    // -----------------------------------


    @Transactional(readOnly = true)
    public List<AnomaliaResponseDTO> listar(statusAnomalia status, nivelRisco risco) {


        List<Anomalia> anomalias;

        if(status != null) {
            anomalias = anomaliaRepository.findByStatus(status);
        }
        else if(risco != null) {
            anomalias= anomaliaRepository.findByRisco(risco);
        }
        else {
            anomalias = anomaliaRepository.findAll();
        }

        ArrayList<AnomaliaResponseDTO> listagem = new ArrayList<>();

        for (Anomalia anomalia : anomalias) {
            listagem.add(new AnomaliaResponseDTO(anomalia));
        }

        return listagem;

    }


    // -----------------------------------


	@Transactional(readOnly = true)
    public List<AnomaliaResponseDTO> listarAnomaliasAtivas() {
    
        List<Anomalia> listaAtivas = anomaliaRepository.findByStatusIn(STATUS_ATIVOS);

        ArrayList<AnomaliaResponseDTO> listaAnomalias = new ArrayList<>();

        for(Anomalia a : listaAtivas) {

            listaAnomalias.add(new AnomaliaResponseDTO(a));
 
        }

        return listaAnomalias;
    }


    // -----------------------------------

    // teoricamente ver se ta ativa e risco critico pq mesmo q seja critica pode estar resolvida 
    // puxar por ambas 
	@Transactional(readOnly = true)
    public List<AnomaliaResponseDTO> listarAnomaliasCriticas() {
        

        List<Anomalia> anomaliasCriticas = anomaliaRepository.findByStatusInAndRisco(STATUS_ATIVOS, nivelRisco.CRITICO);

        ArrayList<AnomaliaResponseDTO> listaAnomalias = new ArrayList<>();

        for(Anomalia a : anomaliasCriticas) {

            listaAnomalias.add(new AnomaliaResponseDTO(a));
        }

        return listaAnomalias;
        
    }

    // -----------------------------------


	@Transactional(readOnly = true)
    public List<RegistroHistoricoResponseDTO> listarHistorico(Long id) {

    // melhorar verificacao aq
        buscarEntidade(id);

    return historicoService.listarPorAnomalia(id);
    }   

    // -----------------------------------

    @Transactional
    public AnomaliaResponseDTO criar(AnomaliaRequestDTO dto) {

        Optional<EventoHIstorico> evento = eventoRepository.findById(dto.getEventoID());

        if(evento.isPresent() == false) {
            throw new RecursoNaoEncontradoException("O evento" + evento + "Não foi encontrado");
        }
        
        Anomalia anomalia = new Anomalia();
        anomalia.setDivergencia(dto.getDivergencia());
        anomalia.setRisco(dto.getRisco());
        anomalia.setOrigem(dto.getOrigem());
        anomalia.setObservacoes(dto.getObservacoes());
        anomalia.setEvento(evento.get());
        
        Anomalia salva = anomaliaRepository.save(anomalia);


         historicoService.registrar(salva.getId(), tipoRegistro.ANOMALIA_REGISTRADA, "sistema", "Anomalia no evento" + evento.get().getTitulo(), salva.getObservacoes(), salva.getRisco().name());

        return new AnomaliaResponseDTO(salva);
        }

    // anomalia pertence a 1 evento na ql foi detectado
    //  se eu troca evento muda meio q seria outra anomalia praticamente
 
   
    @Transactional    
    public AnomaliaResponseDTO atualizar(Long id, AnomaliaRequestDTO dto) {

        // divergencia, origem e observacao

        Anomalia anomalia = buscarEntidade(id);

        anomalia.setDivergencia(dto.getDivergencia());
        anomalia.setOrigem(dto.getOrigem());
        anomalia.setObservacoes(dto.getObservacoes());

        return new AnomaliaResponseDTO(anomalia);

    }

    // -----------------------------------


    @Transactional
    public AnomaliaResponseDTO alterarRisco(Long id, AlteracaoRiscoRequest requestDto) {

        Anomalia anomalia = buscarEntidade(id);

        if(!STATUS_ATIVOS.contains(anomalia.getStatus())) {throw new ConflitoRegraNegociosException("Não da pra atualizar anomalia q n ta ativa");
        }

        nivelRisco riscoAnomaliaAntigo = anomalia.getRisco();

        anomalia.setRisco(requestDto.getRisco()); 

        historicoService.registrar(id, tipoRegistro.RISCO_ALTERADO, "Sistema", "Risco Alterado", riscoAnomaliaAntigo.name(), anomalia.getRisco().name());
    
        return new AnomaliaResponseDTO(anomalia);
    }

    // -----------------------------------

    @Transactional
    public AnomaliaResponseDTO alterarStatusAnomalia(Long id, AlteracaoStatusAnomaliaRequest requestDto) {


        Anomalia anomalia = buscarEntidade(id);

        if(requestDto.getStatus() == statusAnomalia.EM_CORRECAO) {

            throw new DadosInvalidosException("Só pode botar pra em correcao quando iniciar missao de correção");
   
    }

    if(anomalia.getStatus() == statusAnomalia.ESTABILIZADA || anomalia.getStatus() == statusAnomalia.IRREVERSIVEL ) {

        throw new ConflitoRegraNegociosException("Anomalia em estado final não pode mudar de status");
    }

    if(anomalia.getStatus() == statusAnomalia.EM_CORRECAO) {
        throw new ConflitoRegraNegociosException("Status da anomalia só muda dps de acabar a missao");
    }

    if(requestDto.getStatus() == statusAnomalia.DETECTADA) {

        throw new ConflitoRegraNegociosException("Detectada é o estado inicial da anomalia, ela não pode voltar a ser.");
    }
    
        statusAnomalia valorAntigo = anomalia.getStatus();
        statusAnomalia valorNovo = requestDto.getStatus();
        
        anomalia.setStatus(valorNovo);
        
        historicoService.registrar(id, tipoRegistro.STATUS_ALTERADO, "Sistema", "Status alterado", valorAntigo.name(), valorNovo.name());
    
        return new AnomaliaResponseDTO(anomalia);
    }
}