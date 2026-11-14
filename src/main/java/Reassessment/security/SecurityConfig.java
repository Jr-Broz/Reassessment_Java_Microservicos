package Reassessment.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration 
@EnableWebSecurity 
@RequiredArgsConstructor 
public class SecurityConfig {
  
    private final SecurityFilter securityFilter;

    // criar instancia
    @Bean  
    public SecurityFilterChain securityFilterChain(HttpSecurity http) { // decidi agrupar as rotas que tem a mesma regra

return http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/error").permitAll()

                .requestMatchers(HttpMethod.GET, "/**").authenticated()

                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMINISTRADOR")

                .requestMatchers(HttpMethod.POST, "/missoes/*/intervencoes", "/missoes/*/ocorrencias").hasAnyRole("AGENTE", "SUPERVISOR", "ADMINISTRADOR")

                .requestMatchers(HttpMethod.POST, "/missoes/*/autorizar", "/missoes/*/iniciar", "/missoes/*/encerrar", "/missoes/*/cancelar").hasAnyRole("SUPERVISOR", "ADMINISTRADOR")

                .requestMatchers(HttpMethod.PATCH, "/anomalias/*/risco", "/anomalias/*/status").hasAnyRole("SUPERVISOR", "ADMINISTRADOR")

                .requestMatchers("/agentes/**").hasAnyRole("SUPERVISOR", "ADMINISTRADOR")

                .anyRequest().hasAnyRole("OPERADOR", "SUPERVISOR", "ADMINISTRADOR")
        )
        .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}   
