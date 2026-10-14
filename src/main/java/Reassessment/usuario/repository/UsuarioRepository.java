package Reassessment.usuario.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import Reassessment.usuario.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    
}
