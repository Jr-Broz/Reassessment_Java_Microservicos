package Reassessment.common.exception;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice 
public class GlobalExceptionHandler {
 
    
    private Map<String, Object> montaErro(HttpStatus status, String mensagem) {

      Map<String, Object> erro = new HashMap<>();
      erro.put("timestamp", LocalDateTime.now());
      erro.put("status", status.value());
      erro.put("erro", status.getReasonPhrase());
      erro.put("mensagem", mensagem);

      return erro;
    }

@ExceptionHandler(RecursoNaoEncontradoException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
public Map<String, Object> naoEncontrado(RecursoNaoEncontradoException ex) {

    return montaErro(HttpStatus.NOT_FOUND, ex.getMessage());
}

@ExceptionHandler(DadosInvalidosException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public Map<String, Object> dadosInvalidos(DadosInvalidosException ex) {

    return montaErro(HttpStatus.BAD_REQUEST, ex.getMessage());
}


@ExceptionHandler(MethodArgumentNotValidException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public Map<String, Object> validacao(MethodArgumentNotValidException ex) {

    return montaErro(HttpStatus.BAD_REQUEST, ex.getBindingResult().getFieldError().getField() + ": " + ex.getBindingResult().getFieldError().getDefaultMessage());
}

@ExceptionHandler(HttpMessageNotReadableException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public Map<String, Object> jsonInvalido(HttpMessageNotReadableException ex) {

    return montaErro(HttpStatus.BAD_REQUEST, "json invalido");
}


@ExceptionHandler(ConflitoRegraNegociosException.class)
@ResponseStatus(HttpStatus.CONFLICT)
public Map<String, Object> conflitoRegras(ConflitoRegraNegociosException ex) {

    return montaErro(HttpStatus.CONFLICT, ex.getMessage());
}

@ExceptionHandler(MethodArgumentTypeMismatchException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public Map<String, Object> parametroInvalido(MethodArgumentTypeMismatchException ex) {

    return montaErro(HttpStatus.BAD_REQUEST, ex.getName() + " INVALIDO"); // getMEssage puxa o erro inteiro, getName so o nome do parametro
}



@ExceptionHandler(AccessDeniedException.class)
@ResponseStatus(HttpStatus.FORBIDDEN)
public Map<String, Object> semPermissao(AccessDeniedException ex) {

    return montaErro(HttpStatus.CONFLICT, ex.getMessage());
}

@ExceptionHandler(AuthenticationException.class)
@ResponseStatus(HttpStatus.FORBIDDEN)
public Map<String, Object> naoAutenticado(AuthenticationException ex) {

    return montaErro(HttpStatus.CONFLICT, "Usuario nao autenticado, reveja senha ou login");
}


@ExceptionHandler(RuntimeException.class)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public Map<String, Object> erroInesperado(RuntimeException ex) {

    ex.printStackTrace(); 
    return montaErro(HttpStatus.INTERNAL_SERVER_ERROR, "erro inesperado do servidor");
}


}