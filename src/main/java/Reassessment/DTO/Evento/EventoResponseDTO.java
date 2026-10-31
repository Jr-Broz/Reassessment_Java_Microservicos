package Reassessment.DTO.Evento;

import java.time.LocalDate;

import Reassessment.enums.Importancia;
import Reassessment.evento.EventoHIstorico;
import lombok.Getter;

@Getter 
public class EventoResponseDTO {
 
    
    Long id;
    String titulo;
    String descricao;
    LocalDate data; // Data de inicio ou fim? Checar isso
    String localizacao;
    Importancia importancia;



    public  EventoResponseDTO(EventoHIstorico evento) {

        this.id = evento.getId();
        this.titulo = evento.getTitulo();
        this.descricao = evento.getDescricao();
        this.data = evento.getData();
        this.localizacao = evento.getLocalizacao();
        this.importancia = evento.getImportancia();
    }





}