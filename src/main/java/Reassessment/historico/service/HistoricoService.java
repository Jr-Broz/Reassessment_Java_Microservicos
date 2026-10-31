package Reassessment.historico.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Reassessment.DTO.Historico.RegistroHistoricoResponseDTO;
import Reassessment.enums.tipoRegistro;
import Reassessment.historico.RegistroHistorico;
import Reassessment.historico.repository.HistoricoRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor  
public class HistoricoService {

    private final HistoricoRepository historicoRepository;

 
    
    public void registrar(Long anomaliaId, tipoRegistro tipo, String usuario, String descricao, String valorAnterior, String valorNovo) {

        RegistroHistorico registro = new RegistroHistorico();

        
        registro.setAnomaliaId(anomaliaId);
        registro.setTipo_Registro(tipo);
        registro.setUsuario(usuario);
        registro.setDescricaoRegistro(descricao);
        registro.setValorAnterior(valorAnterior);
        registro.setValorNovo(valorNovo);
        historicoRepository.save(registro);
    }


    @Transactional(readOnly = true)
    public List<RegistroHistoricoResponseDTO> listarPorAnomalia(Long anomaliaId) {

        List<RegistroHistorico> registroHistorico;

        registroHistorico = historicoRepository.findByAnomaliaIdOrderByInstanteAsc(anomaliaId);

        ArrayList<RegistroHistoricoResponseDTO> listagem = new ArrayList<>();

        for(RegistroHistorico r : registroHistorico) {
            listagem.add(new RegistroHistoricoResponseDTO(r));

        }
        return listagem;
    }





}
