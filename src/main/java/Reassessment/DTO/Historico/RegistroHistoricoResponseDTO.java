package Reassessment.DTO.Historico;
import java.time.LocalDateTime;
import Reassessment.enums.tipoRegistro;

public class RegistroHistoricoResponseDTO {
    
    tipoRegistro tipo;
    LocalDateTime instante;
    String usuario;
    String descricao;
    String valorAnterior;
    String valorNovo;
}