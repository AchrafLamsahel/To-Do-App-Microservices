package org.tasksmicroservice.exceptions.exceptionHandler;

import org.modelmapper.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.tasksmicroservice.exceptions.TaskAlreadyExistsException;
import org.tasksmicroservice.exceptions.TaskNotFoundException;
import org.tasksmicroservice.exceptions.UserNotFoundException;

@ControllerAdvice
public class TaskExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {TaskNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RuntimeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(ValidationException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TaskAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public String handleValidationException(TaskAlreadyExistsException ex) {
        return ex.getMessage();
    }



}

