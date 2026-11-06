package Reassessment.usuario.controller;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Reassessment.DTO.Usuario.LoginRequestDTO;
import Reassessment.DTO.Usuario.LoginResponseDTO;
import Reassessment.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController 
@RequestMapping("/auth")
@RequiredArgsConstructor 
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid  @RequestBody LoginRequestDTO dto) {

        var  usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.senha());    
    
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.gerarToken(auth.getName());

        return new LoginResponseDTO(token);
    }

}
