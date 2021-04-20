package com.comcast.lcs.exception;

import com.comcast.lcs.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class LcsExceptionHandler {

  @Order(Ordered.HIGHEST_PRECEDENCE)
  @ExceptionHandler(value = {MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<ErrorDto> handleConstraintViolationException(Exception ex) {
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("error.invalid.request");
    errorDto.setMessage("Invalid request");

    if (ex instanceof MethodArgumentNotValidException) {
      errorDto.setDetails("Ensure set of strings is not empty");
    } else if (ex instanceof HttpMessageNotReadableException) {
      errorDto.setDetails("Ensure request body has correct format or not empty");
    }

    return ResponseEntity.badRequest().body(errorDto);
  }

  @ExceptionHandler(value = {BadRequestException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorDto> handleConstraintViolationException(BadRequestException ex) {
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("error.invalid.request");
    errorDto.setMessage("Invalid request");
    errorDto.setDetails(ex.getMessage());

    return ResponseEntity.badRequest().body(errorDto);
  }

  @ExceptionHandler(value = {RuntimeException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorDto> handleException(RuntimeException re) {
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("error.internal.server");
    errorDto.setMessage("Internal Server");
    errorDto.setDetails(re.getMessage());
    log.error("Internal Server Error", re);
    return ResponseEntity.badRequest().body(errorDto);
  }
}
