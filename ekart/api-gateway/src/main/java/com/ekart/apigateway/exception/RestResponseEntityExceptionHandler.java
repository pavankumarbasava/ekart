package com.ekart.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("config")
public class RestResponseEntityExceptionHandler  {

  
    
    @ExceptionHandler(UnauthorisedException.class)
    protected ResponseEntity<String> handleResourceNotFound(UnauthorisedException ex){

      return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body("Please provide proper authentication");
    }

}
