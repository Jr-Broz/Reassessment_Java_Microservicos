package Reassessment.missao.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Reassessment.DTO.Missao.MissaoRequestDTO;
import Reassessment.DTO.Missao.MissaoResponseDTO;
import Reassessment.agente.repository.AgenteRepository;
import Reassessment.anomalia.Anomalia;
import Reassessment.anomalia.repository.AnomaliaRepository;
import Reassessment.anomalia.service.AnomaliaService;
import Reassessment.common.exception.ConflitoRegraNegociosException;
import Reassessment.common.exception.RecursoNaoEncontradoException;
import Reassessment.enums.statusMissao;
import Reassessment.enums.tipoRegistro;
import Reassessment.historico.service.HistoricoService;
import Reassessment.missao.Missao;
import Reassessment.missao.repository.MissaoRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor  
public class MissaoService {
 
    private final MissaoRepository missaoRepository;
    private final AnomaliaRepository anomaliaRepository;
    private final AgenteRepository agenteRepository;
    private final HistoricoService historicoService;


    private Missao buscarEntidade(Long id ) {
        return missaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Missao com ID: " + id + "  nao foi achado"));

    }   
    @Transactional(readOnly = true)
    public List<MissaoResponseDTO> listar(statusMissao status) {

        List<Missao> missoes;

        if(status == null) {
            missoes = missaoRepository.findAll();
        }
        else {
            missoes = missaoRepository.findByStatus(status);
        }

        ArrayList<MissaoResponseDTO> listaMissoes = new ArrayList<>();

        for(Missao m : missoes) {
            listaMissoes.add(new MissaoResponseDTO(m));
        }

        return listaMissoes;
    }

    @Transactional(readOnly = true)
    public List<MissaoResponseDTO> listarEmExecucao() {

        List<Missao> missoesAtivas = missaoRepository.findByStatus(statusMissao.EM_EXECUCAO);
    
        ArrayList<MissaoResponseDTO> listagemMissoes = new ArrayList<>();

        for(Missao missao : missoesAtivas) {
            listagemMissoes.add(new MissaoResponseDTO(missao));
        }

        return listagemMissoes;

    }
    @Transactional(readOnly = true)
    public MissaoResponseDTO buscarPorId(Long Id){

        Missao missao = buscarEntidade(Id);
        return new MissaoResponseDTO(missao);

    }
    @Transactional(readOnly = true)
    public List<MissaoResponseDTO> listarPorAnomalia(Long anomaliaId) {

        if(!anomaliaRepository.existsById(anomaliaId)) {
            throw new RecursoNaoEncontradoException("Anomalia com o ID:  " + anomaliaId + " Nao foi achado");
        }

        List<Missao> missaoAnomalia = missaoRepository.findByAnomaliaId(anomaliaId);

        ArrayList<MissaoResponseDTO> listaMissoesAnomalias = new ArrayList<>();

        for (Missao missao : missaoAnomalia) {

            listaMissoesAnomalias.add(new MissaoResponseDTO(missao));
        }

        return listaMissoesAnomalias;
        
    }
    @Transactional
    public List<MissaoResponseDTO> listarPorAgente(Long agenteId) {

         if(!agenteRepository.existsById(agenteId)) {
            throw new RecursoNaoEncontradoException("Agente com o ID:  " + agenteId + " Nao foi achado");
        }

     List<Missao> missaoAgente = missaoRepository.findByAgentesId(agenteId);

    ArrayList<MissaoResponseDTO> listaMissoesAgentes = new ArrayList<>();

    for(Missao missaoAgnt : missaoAgente) {
        listaMissoesAgentes.add(new MissaoResponseDTO(missaoAgnt));
    }
    
    return listaMissoesAgentes;
}

    @Transactional
    public MissaoResponseDTO criar(MissaoRequestDTO dto){

        Anomalia anomalia = anomaliaRepository.findById(dto.getAnomaliaId()).orElseThrow(() -> new RecursoNaoEncontradoException("Anomalia com ID: " + dto.getAnomaliaId() + " nao foi achado"));

        if(!AnomaliaService.STATUS_ATIVOS.contains(anomalia.getStatus())) {
            throw new ConflitoRegraNegociosException("essa anomalia nao aceita novas missoes");
        }

        if(missaoRepository.existsByCodigoMissao(dto.getCodigo())) {
            throw new ConflitoRegraNegociosException("ja existe missao com o codigo   " + dto.getCodigo());
        }

        Missao salva = new Missao();

        salva.setCodigoMissao(dto.getCodigo());
        salva.setObjetivo(dto.getObjetivo());
        salva.setAnomalia(anomalia);
        salva.setDestinoData(dto.getDestinoData());
        salva.setDestinoLocal(dto.getDestinoLocal());
        salva.setPrioridade(dto.getPrioridade());
        salva.setTipo(dto.getTipo());

        missaoRepository.save(salva);

        historicoService.registrar(anomalia.getId(), tipoRegistro.MISSAO_CRIADA, "SISTEMA", "Missao " + dto.getCodigo() + " criada ", null, dto.getCodigo());

        return  new MissaoResponseDTO(salva);
    }

    @Transactional
    public MissaoResponseDTO atualizar(Long id, MissaoRequestDTO dto) {

       Missao missao =  buscarEntidade(id);

        if(missao.getStatus() != statusMissao.PLANEJADA) {
            throw new ConflitoRegraNegociosException("Missao que nao ta planejada nao pode mudar ");
        }
    
        missao.setObjetivo(dto.getObjetivo());
        missao.setDestinoData(dto.getDestinoData());
        missao.setDestinoLocal(dto.getDestinoLocal());
        missao.setPrioridade(dto.getPrioridade());
        missao.setTipo(dto.getTipo());

        return new MissaoResponseDTO(missao);
    }
}