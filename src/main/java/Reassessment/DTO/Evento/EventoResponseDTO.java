package Reassessment.DTO.Evento;

import java.time.LocalDate;

import Reassessment.enums.Importancia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EventoResponseDTO {
 
    
    @NotNull 
    Long id;

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