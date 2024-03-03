package com.blog_rest_api.springbootrestapi.exception;

import com.blog_rest_api.springbootrestapi.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //enables the class to handle exceptions globally
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, // Exception thrown when @Valid annotation validation fails
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>(); // A map to hold field names and their validation error messages

        // Iterate over all errors. Each error corresponds to a validation failure.
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField(); // Get the field name where validation failed
            String message = error.getDefaultMessage(); // Get the default error message for this validation failure
            errors.put(fieldName, message); // Add the field name and error message to the map
        });

        // Return a ResponseEntity with the errors map as the body and set the HTTP status to BAD_REQUEST (400)
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
