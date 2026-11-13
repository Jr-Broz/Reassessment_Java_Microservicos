package Reassessment.ocorrencia;
import java.time.LocalDateTime;
import java.util.Map;
import Reassessment.agente.Agente;
import Reassessment.enums.tipoOcorrencia;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter 
@Setter 
@NoArgsConstructor 
@Document(collection = "ocorrencias")
public class Ocorrencia {
 
    @Id 
    private String id;
    private Long missaoId;
    private Long agenteId;
    private String agenteNome;
    private tipoOcorrencia tipo;
    private LocalDateTime  instante;
    private String descricao;
    private Map<String, Object> detalhes;

}