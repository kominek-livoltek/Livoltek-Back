package br.com.livoltek.core.internal.common.exception;

public class NaoAutorizadoException extends RuntimeException {

    public NaoAutorizadoException(String role) {
        super(String.format("Usuário não autorizado. Role %s necessária.", role));
    }
}