package com.product.oneforall.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Objects;

import static com.product.oneforall.constants.OneForAllConstants.MESSAGE;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        HashMap<String,Object> response = new HashMap<>();
        response.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PasswordLengthException.class)
    public ResponseEntity<Object> passworldLengthException(Exception ex) {
        HashMap<String,Object> response = new HashMap<>();
        response.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Object> passwordMismatchException(Exception ex) {
        HashMap<String,Object> response = new HashMap<>();
        response.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> userAlreadyExistException(Exception ex) {
        HashMap<String,Object> response = new HashMap<>();
        response.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
@Data
class ErrorResponse{
    private String message;
    private String status;
}

