package Reassessment.usuario.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import Reassessment.usuario.Usuario;
import Reassessment.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UsuarioDetailsService implements UserDetailsService{
    
    private final UsuarioRepository usuarioRepository;

    @Override 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " N foi encontrado"));

        return User.withUsername(usuario.getUsername()).password(usuario.getSenha()).roles(usuario.getPerfil().name()).build();
    }

  

}
