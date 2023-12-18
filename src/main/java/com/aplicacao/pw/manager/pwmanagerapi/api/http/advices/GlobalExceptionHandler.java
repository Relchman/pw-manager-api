package com.aplicacao.pw.manager.pwmanagerapi.api.http.advices;

import com.aplicacao.pw.manager.pwmanagerapi.domain.exception.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String TIMESTAMP_PROPERTY = "TimeStamp";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode httpStatusCode,
                                                                  WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatusCode, ex.getLocalizedMessage());
        problemDetail.setTitle("Erro na conversão dos dados");
        problemDetail.setProperty(TIMESTAMP_PROPERTY, Instant.now());
        problemDetail.setDetail(ex.getLocalizedMessage());

        return new ResponseEntity<>(problemDetail, headers, httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("Erro na validação dos dados");
        problemDetail.setProperty(TIMESTAMP_PROPERTY, Instant.now());
        problemDetail.setProperty("erros", ex.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList());

        return new ResponseEntity<>(problemDetail, headers, status);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ProblemDetail handleNotFoundException(RuntimeException ex) {
        return createProblemDetail(ex, HttpStatus.BAD_REQUEST);
    }


    private static ProblemDetail createProblemDetail(RuntimeException ex, HttpStatus httpStatus) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, ex.getLocalizedMessage());
        problemDetail.setTitle("Erro");
        problemDetail.setProperty(TIMESTAMP_PROPERTY, Instant.now());

        return problemDetail;
    }
}
