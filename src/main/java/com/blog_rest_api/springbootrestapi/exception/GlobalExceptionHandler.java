package com.blog_rest_api.springbootrestapi.exception;

import com.blog_rest_api.springbootrestapi.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice //enables the class to handle exceptions globally
public class GlobalExceptionHandler {
    //here we can handle global as well as specific instructions

    //Specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class) //used for specific exceptions
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                e.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //Global Exceptions
    @ExceptionHandler(Exception.class) //used for specific exceptions
    public ResponseEntity<ErrorDetails> handleGlobalExceptions(Exception e, WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                e.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
