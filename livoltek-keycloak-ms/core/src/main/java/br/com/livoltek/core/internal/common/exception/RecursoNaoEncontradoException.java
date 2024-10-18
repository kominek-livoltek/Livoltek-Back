package br.com.livoltek.core.internal.common.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String tipoObjeto) {
        super(String.format("%s não encontrado(a) com os parâmetros especificados", tipoObjeto));
    }

    public RecursoNaoEncontradoException() {
        super("Recurso não encontrado(a) com os parâmetros especificados");
    }
}