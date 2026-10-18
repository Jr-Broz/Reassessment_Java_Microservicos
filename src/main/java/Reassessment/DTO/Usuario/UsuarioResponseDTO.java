package Reassessment.DTO.Usuario;

import Reassessment.agente.Agente;
import Reassessment.enums.Perfil;
import Reassessment.usuario.Usuario;

public class UsuarioResponseDTO {
    
    Long id;
    String username;
    Perfil perfil;
    Agente agenteID;


   public UsuarioResponseDTO(Usuario usuario) {

    this.id = usuario.getId();
    this.username = usuario.getUsername();
    this.perfil = usuario.getPerfil();
    this.agenteID = usuario.getAgente();
   }

}
