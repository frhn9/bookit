package com.enigma.bookit.exception;

import com.enigma.bookit.constant.ErrorMessageConstant;
import com.enigma.bookit.constant.ResponseLabelConstant;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends RuntimeException{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        Map<String, String> fieldErrorList = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(ResponseLabelConstant.LABEL_TIMESTAMP, LocalDateTime.now());
            errors.put(ResponseLabelConstant.LABEL_STATUS, HttpStatus.BAD_REQUEST.name());
            errors.put(ResponseLabelConstant.LABEL_CODE, HttpStatus.BAD_REQUEST.value());

            String errorFieldList = ((FieldError) error).getField();
            String errorValueList = error.getDefaultMessage();
            fieldErrorList.put(errorFieldList, errorValueList);
            errors.put(ResponseLabelConstant.LABEL_MESSAGE, fieldErrorList);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class})
    public Map<String, Object> handleNoSuchElementOrEmptyResultException() {
        Map<String, Object> errors = new HashMap<>();

        errors.put(ResponseLabelConstant.LABEL_CODE, HttpStatus.NOT_FOUND.value());
        errors.put(ResponseLabelConstant.LABEL_STATUS, HttpStatus.NOT_FOUND.name());
        errors.put(ResponseLabelConstant.LABEL_MESSAGE, ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED);
        errors.put(ResponseLabelConstant.LABEL_TIMESTAMP, LocalDateTime.now());

        return errors;
    }

}
