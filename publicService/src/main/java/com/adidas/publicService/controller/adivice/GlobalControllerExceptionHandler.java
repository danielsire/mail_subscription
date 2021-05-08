package com.adidas.publicService.controller.adivice;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignConflict(FeignException ex, final HttpServletRequest request) {
        logger.error(request.getRequestURL().toString(), ex);
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class, ConnectException.class })
    public ResponseEntity<Object> handleInternalServerError(final Exception ex, final HttpServletRequest request) {
        logger.error(request.getRequestURL().toString(), ex);
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleConflict(final RuntimeException ex, final HttpServletRequest request) {
        logger.error(request.getRequestURL().toString(), ex);
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }

}
