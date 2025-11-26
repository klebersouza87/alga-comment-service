package com.ems.algacomments.comment.service.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty(TIMESTAMP, OffsetDateTime.now().toString());
        problemDetail.setTitle("Validation Failed");
        StringBuilder detailBuilder = new StringBuilder("Validation errors:");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                detailBuilder.append(" [").append(error.getField()).append(": ").append(error.getDefaultMessage()).append("]"));
        problemDetail.setDetail(detailBuilder.toString());
        return problemDetail;
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ProblemDetail handleHttpClientErrorException(HttpClientErrorException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setProperty(TIMESTAMP, OffsetDateTime.now().toString());
        problemDetail.setTitle("Moderation Service Communication Error");
        problemDetail.setDetail("Error occurred while communicating with the Moderation Service to validate comment. Comment contains not allowed words");
        return problemDetail;
    }

    @ExceptionHandler(RestClientException.class)
    public ProblemDetail handleRestClientException(RestClientException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);
        problemDetail.setProperty(TIMESTAMP, OffsetDateTime.now().toString());
        problemDetail.setTitle("Gateway timeout");
        problemDetail.setDetail("Failed to communicate with an external service. " + ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatusException(ResponseStatusException ex) {
        if (ex.getBody().getStatus() == 422) {
            ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
            problemDetail.setProperty(TIMESTAMP, OffsetDateTime.now().toString());
            problemDetail.setTitle("Comment Not Approved");
            problemDetail.setDetail("Error: " + ex.getMessage());
            return problemDetail;
        }
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setProperty(TIMESTAMP, OffsetDateTime.now().toString());
        problemDetail.setTitle("Comment Not Found");
        problemDetail.setDetail("Error: " + ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        problemDetail.setProperty(TIMESTAMP, OffsetDateTime.now().toString());
        problemDetail.setTitle("General Error Occurred");
        problemDetail.setDetail("General error occurred: " + ex.getMessage());
        return problemDetail;
    }

}
