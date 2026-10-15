package Reassessment.usuario.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import Reassessment.usuario.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    
    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByAgenteId(Long usuario_id);









}
