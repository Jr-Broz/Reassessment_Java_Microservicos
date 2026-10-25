package Reassessment.anomalia.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Reassessment.DTO.Anomalia.AlteracaoRiscoRequest;
import Reassessment.DTO.Anomalia.AlteracaoStatusAnomaliaRequest;
import Reassessment.DTO.Anomalia.AnomaliaRequestDTO;
import Reassessment.DTO.Anomalia.AnomaliaResponseDTO;
import Reassessment.DTO.Historico.RegistroHistoricoResponseDTO;
import Reassessment.anomalia.Anomalia;
import Reassessment.anomalia.repository.AnomaliaRepository;
import Reassessment.common.exception.RecursoNaoEncontradoException;
import Reassessment.enums.nivelRisco;
import Reassessment.enums.statusAnomalia;
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


    public static final List<statusAnomalia> STATUS_ATIVOS = List.of(statusAnomalia.DETECTADA, statusAnomalia.EM_ANALISE, statusAnomalia.CONFIRMADA, statusAnomalia.EM_CORRECAO, statusAnomalia.ESTABILIZADA, statusAnomalia.IRREVERSIVEL);

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

    }   

// -----------------------------------

    @Transactional
    public AnomaliaResponseDTO criar(AnomaliaRequestDTO dto) {

    }


   @Transactional
    public AnomaliaResponseDTO atualizar(Long id, AnomaliaRequestDTO dto) {

    }

    // -----------------------------------


    @Transactional
    public AnomaliaResponseDTO alterarRisco(Long id, AlteracaoRiscoRequest requestDto) {

    }




    // -----------------------------------

    @Transactional
    public AnomaliaResponseDTO alterarStatusAnomalia(Long id, AlteracaoStatusAnomaliaRequest requestDto) {

    }













}