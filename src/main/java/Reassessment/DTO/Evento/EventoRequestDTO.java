package Reassessment.DTO.Evento;
import java.time.LocalDate;
import Reassessment.enums.Importancia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class EventoRequestDTO {

    @NotBlank 
    String titulo;
    
    String descricao;

    @NotNull 
    LocalDate data;
    
    @NotBlank 
    String localizacao;


    @NotNull 
    Importancia importancia;

}