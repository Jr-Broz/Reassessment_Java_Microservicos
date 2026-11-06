package Reassessment.security;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import Reassessment.usuario.service.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class SecurityFilter extends OncePerRequestFilter{
    
private final TokenService tokenService;
private final UsuarioDetailsService usuarioDetailsService;


    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException{

        String token = recuperarToken(request);

        if(token != null) {
            String username = tokenService.validarToken(token);

            if(!username.isEmpty()) {
                UserDetails usuario = usuarioDetailsService.loadUserByUsername(username);

                var autenticacao = new UsernamePasswordAuthenticationToken(username, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autenticacao);

                
            }

        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null) {
            return null;
        }

        return authHeader.replace("Bearer", "");
    }



}
