package br.com.livoltek.application.resources.exception;

import br.com.livoltek.core.internal.common.exception.GenericException;
import br.com.livoltek.core.internal.common.exception.NaoAutorizadoException;
import br.com.livoltek.core.internal.common.exception.RecursoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ExceptionResponse handleResourceNotFound(
            final RecursoNaoEncontradoException exception,
            final HttpServletRequest request) {
        return new ExceptionResponse(exception.getMessage());
    }
    @ExceptionHandler(GenericException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleGeneric(
            final GenericException exception,
            final HttpServletRequest request) {
        return new ExceptionResponse(exception.getMessage());
    }

    @ExceptionHandler(NaoAutorizadoException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public @ResponseBody ExceptionResponse handleForbidden(
            final NaoAutorizadoException exception,
            final HttpServletRequest request) {
        return new ExceptionResponse(exception.getMessage());
    }
}
