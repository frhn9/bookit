package com.enigma.bookit.exception;

import com.enigma.bookit.utils.ResponseMessageFiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FilesUploadException extends ResponseEntityExceptionHandler {

    @ExceptionHandler (MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessageFiles> handleMaxSizeException(MaxUploadSizeExceededException exc){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessageFiles("File too large!"));
    }

}
