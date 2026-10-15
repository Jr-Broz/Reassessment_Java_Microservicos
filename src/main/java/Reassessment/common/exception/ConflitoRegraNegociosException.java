package Reassessment.common.exception;

// 7, 8, 19, 5 e 3
public class ConflitoRegraNegociosException extends RuntimeException{
    
    public ConflitoRegraNegociosException(String mensagem) {
        super(mensagem);
    }
}
//https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html
// Any RuntimeException or Error triggers rollback, and any checked Exception does not.

// sessao 9 e 21 d moodle