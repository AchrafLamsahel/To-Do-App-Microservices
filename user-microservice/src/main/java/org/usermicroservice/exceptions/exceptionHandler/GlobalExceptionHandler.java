package org.usermicroservice.exceptions.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.usermicroservice.dto.ErrorBody;
import org.usermicroservice.exceptions.DataNotValidException;
import org.usermicroservice.exceptions.EmailAlreadyExistsException;
import org.usermicroservice.exceptions.EmptyEntityException;
import org.usermicroservice.exceptions.UserNotFoundException;
import java.util.Collections;
import java.util.Date;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(EmailAlreadyExistsException ex) {
        ErrorBody errorBody = new ErrorBody(new Date(), HttpStatus.NOT_ACCEPTABLE, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBody, errorBody.getStatus());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        ErrorBody errorBody = new ErrorBody(new Date(), HttpStatus.NOT_FOUND, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBody, errorBody.getStatus());    }

    @ExceptionHandler(DataNotValidException.class)
    public ResponseEntity<?> handleDataNotValidException(DataNotValidException ex) {
        ErrorBody errorBody = new ErrorBody(new Date(), HttpStatus.NOT_ACCEPTABLE, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBody, errorBody.getStatus());    }

    @ExceptionHandler(EmptyEntityException.class)
    public ResponseEntity<?> emptyEntryExceptionHandler(EmptyEntityException ex){
        ErrorBody errorBody = new ErrorBody(new Date(), HttpStatus.NOT_ACCEPTABLE, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBody, errorBody.getStatus());
    }
}
