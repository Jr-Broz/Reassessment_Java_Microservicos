package Reassessment.DTO.Historico;
import java.time.LocalDateTime;
import Reassessment.enums.tipoRegistro;
import Reassessment.historico.RegistroHistorico;

public class RegistroHistoricoResponseDTO {
    
    tipoRegistro tipo;
    LocalDateTime instante;
    String usuario;
    String descricao;
    String valorAnterior;
    String valorNovo;


    public  RegistroHistoricoResponseDTO(RegistroHistorico historico) {

        this.tipo = historico.getTipo_Registro();
        this.instante = historico.getInstante();
        this.usuario = historico.getUsuario();
        this.descricao = historico.getDescricaoRegistro();
        this.valorAnterior = historico.getValorAnterior();
        this.valorNovo = historico.getValorNovo();
    }
}