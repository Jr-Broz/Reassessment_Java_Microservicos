package Reassessment.Infnet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Reassessment.DTO.Encerramento.EncerramentoRequestDTO;
import Reassessment.agente.Agente;
import Reassessment.agente.repository.AgenteRepository;
import Reassessment.anomalia.Anomalia;
import Reassessment.common.exception.ConflitoRegraNegociosException;
import Reassessment.common.exception.DadosInvalidosException;
import Reassessment.enums.Prioridade;
import Reassessment.enums.nivelRisco;
import Reassessment.enums.statusAgente;
import Reassessment.enums.statusAnomalia;
import Reassessment.enums.statusMissao;
import Reassessment.enums.tipoMissao;
import Reassessment.historico.service.HistoricoService;
import Reassessment.missao.Missao;
import Reassessment.missao.Autorizacao.AutorizacaoRepository.AutorizacaoRepository;
import Reassessment.missao.Encerramento.EncerramentoRepository.EncerramentoRepository;
import Reassessment.missao.repository.MissaoRepository;
import Reassessment.missao.service.OperacaoMissaoService;
import Reassessment.usuario.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class InfnetApplicationTests {

    @Mock MissaoRepository missaoRepository;
    @Mock AgenteRepository agenteRepository;
    @Mock HistoricoService historicoService;
    @Mock AutorizacaoRepository autorizacaoRepository;
    @Mock UsuarioRepository usuarioRepository;
    @Mock EncerramentoRepository encerramentoRepository;

    @InjectMocks OperacaoMissaoService service;


    private Missao criarMissao(statusMissao status, Prioridade prioridade) {
        Anomalia anomalia = new Anomalia();
        anomalia.setId(10L);
        anomalia.setRisco(nivelRisco.MODERADO);
        anomalia.setStatus(statusAnomalia.CONFIRMADA);

        Missao missao = new Missao();
        missao.setId(1L);
        missao.setCodigoMissao("MIS-TESTE");
        missao.setTipo(tipoMissao.CORRECAO);
        missao.setStatus(status);
        missao.setPrioridade(prioridade);
        missao.setAnomalia(anomalia);
        return missao;
    }

    private Agente criarAgente(Long id, statusAgente status) {
        Agente agente = new Agente();
        agente.setId(id);
        agente.setNome("Agente " + id);
        agente.setStatusDoAgente(status);
        return agente;
    }


    @Test
    @DisplayName("N deixa iniciar missao sem agente")
    void iniciarSemAgente() {
        Missao missao = criarMissao(statusMissao.AUTORIZADA, Prioridade.NORMAL);
        when(missaoRepository.findById(1L)).thenReturn(Optional.of(missao));

        assertThrows(ConflitoRegraNegociosException.class, () -> service.iniciar(1L, "supervisor"));
    }

    @Test
    @DisplayName("Agente ocupado impede o início e nada e registrado")
    void iniciarComAgenteOcupado() {
        Missao missao = criarMissao(statusMissao.AUTORIZADA, Prioridade.NORMAL);
        missao.getAgentes().add(criarAgente(1L, statusAgente.EM_MISSAO));
        when(missaoRepository.findById(1L)).thenReturn(Optional.of(missao));

        assertThrows(ConflitoRegraNegociosException.class, () -> service.iniciar(1L, "supervisor"));
        verifyNoInteractions(historicoService);
    }

    @Test
    @DisplayName("Iniciar muda missao, agentes e anomalia")
    void iniciarComSucesso() {
        Missao missao = criarMissao(statusMissao.AUTORIZADA, Prioridade.NORMAL);
        missao.getAgentes().add(criarAgente(1L, statusAgente.DISPONIVEL));
        missao.getAgentes().add(criarAgente(2L, statusAgente.DISPONIVEL));
        when(missaoRepository.findById(1L)).thenReturn(Optional.of(missao));

        service.iniciar(1L, "supervisor");

        assertEquals(statusMissao.EM_EXECUCAO, missao.getStatus());
        assertEquals(statusAgente.EM_MISSAO, missao.getAgentes().get(0).getStatusDoAgente());
        assertEquals(statusAgente.EM_MISSAO, missao.getAgentes().get(1).getStatusDoAgente());
        assertEquals(statusAnomalia.EM_CORRECAO, missao.getAnomalia().getStatus());
        verify(historicoService, times(2)).registrar(any(), any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("Missão que falhou n pode estabilizar a anomalia")
    void encerrarFalhaEstabilizando() {
        Missao missao = criarMissao(statusMissao.EM_EXECUCAO, Prioridade.NORMAL);

        EncerramentoRequestDTO dto = new EncerramentoRequestDTO();
        dto.setResultadoMissao(statusMissao.FALHOU);
        dto.setSituacaoFinalAnomalia(statusAnomalia.ESTABILIZADA);

        when(missaoRepository.findById(1L)).thenReturn(Optional.of(missao));

        assertThrows(DadosInvalidosException.class, () -> service.encerrar(1L, dto, "supervisor"));
    }
}