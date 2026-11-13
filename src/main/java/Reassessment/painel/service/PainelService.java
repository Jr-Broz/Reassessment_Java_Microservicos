package Reassessment.painel.service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import Reassessment.DTO.Painel.EstadoOperacionalResponseDTO;
import Reassessment.DTO.Painel.IndiceEstabilidadeResponseDTO;
import Reassessment.agente.repository.AgenteRepository;
import Reassessment.anomalia.repository.AnomaliaRepository;
import Reassessment.anomalia.service.AnomaliaService;
import Reassessment.enums.estadoLinhaTemporal;
import Reassessment.enums.nivelImpacto;
import Reassessment.enums.nivelRisco;
import Reassessment.enums.statusAgente;
import Reassessment.enums.statusMissao;
import Reassessment.intervencao.repository.IntervencaoRepository;
import Reassessment.missao.repository.MissaoRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor  
public class PainelService {

    private final AgenteRepository agenteRepository;
    private final MissaoRepository missaoRepository;
    private final AnomaliaRepository anomaliaRepository;
    private final IntervencaoRepository intervencaoRepository;
    

    private estadoLinhaTemporal classificar(int indice) {

        if(indice >= 80) {
            return estadoLinhaTemporal.ESTAVEL;
        }
        if(indice >= 50) {
            return estadoLinhaTemporal.ATENCAO;
        }
        
        if(indice >= 20) {
            
            return estadoLinhaTemporal.INSTAVEL;
        }

    return estadoLinhaTemporal.CRITICA;
}
    @Cacheable("indiceEstabilidade")
    public IndiceEstabilidadeResponseDTO indiceEstabilidade() {

        long ativas = anomaliaRepository.countByStatusIn(AnomaliaService.STATUS_ATIVOS);
        long criticas = anomaliaRepository.countByStatusInAndRisco(AnomaliaService.STATUS_ATIVOS, nivelRisco.CRITICO);
        long emExecucao = missaoRepository.countByStatus(statusMissao.EM_EXECUCAO);
        long falhas = missaoRepository.countByStatus(statusMissao.FALHOU);
        long severas = intervencaoRepository.countByImpactoEstimado(nivelImpacto.SEVERO);
        long penalidade = ativas * 5 + criticas * 10 + emExecucao *2 + falhas *8 + severas * 6;
        int indice = (int) Math.max(0, 100 - penalidade);

        return new IndiceEstabilidadeResponseDTO(indice, classificar(indice), ativas, criticas, emExecucao, falhas, severas);
    }


    @Cacheable("estadoOperacional")
    public EstadoOperacionalResponseDTO estadoOperacional() {

        IndiceEstabilidadeResponseDTO indice = indiceEstabilidade();
        long disponiveis = agenteRepository.countByStatusDoAgente(statusAgente.DISPONIVEL);
        long emMissao = agenteRepository.countByStatusDoAgente(statusAgente.EM_MISSAO);

        return new EstadoOperacionalResponseDTO(disponiveis, emMissao, indice.missoesEmExecucao(), indice.anomaliasAtivas(), indice.anomaliasCriticas(), indice.indice(), indice.estado());

    }
}