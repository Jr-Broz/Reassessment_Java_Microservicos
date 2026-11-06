package Reassessment.security;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;


@Service 
public class TokenService {

    
    @Value("${jwt.secret}")
    String secret;


    @Value("${jwt.expiracao-minutos}")
    long expiracaoMinutos;


    public String gerarToken(String username) {

    try {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create().withIssuer("continuum-control").withSubject(username).withExpiresAt(gerarDataExpiracao()).sign(algorithm);

    }
    catch(JWTCreationException exception) {
        throw new RuntimeException("Houve um erro na hora de gerar o token:  ", exception);
    }

    }

    public String validarToken(String token){

        try{ 

        Algorithm algorithm =  Algorithm.HMAC256(secret);

        return JWT.require(algorithm).withIssuer("continuum-control").build().verify(token).getSubject();

        }
        catch(JWTVerificationException exception) {
            return "";
        }
    }

    private Instant gerarDataExpiracao() {
        return Instant.now().plus(expiracaoMinutos, ChronoUnit.MINUTES);
    }

    
}
