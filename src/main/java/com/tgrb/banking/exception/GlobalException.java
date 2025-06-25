package com.tgrb.banking.exception;

import com.tgrb.banking.utility.APIResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(CustomException_Employee.class)
    public ResponseEntity<Object> handleKnownCustomException(CustomException_Employee exception){
        APIResponse<Object> errorResponse = new APIResponse<>(false,null,400);
        errorResponse.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String,String> errorsList = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errorsList.put(error.getField(),error.getDefaultMessage()));

        APIResponse<ErrorResponse> errorResponse = new APIResponse<>(false,null,400);
        errorResponse.setErrorMessage("Missing or invalid field : value.");
        errorResponse.setErrors(errorsList);

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception exception){
        APIResponse<Object> errorResponse = new APIResponse<>(false,null,500);
        errorResponse.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Object>> handlePathAndQueryVariableNotValidException(ConstraintViolationException exception){
        APIResponse<Object> errorResponse = new APIResponse<>(false,null,400);

        errorResponse.setErrorMessage(
            exception.getConstraintViolations()
                    .stream()
                    .map(error -> error.getMessage())
                    .collect(Collectors.joining(";\n"))
        );

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
