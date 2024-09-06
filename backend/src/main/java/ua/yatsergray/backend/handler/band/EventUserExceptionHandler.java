package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.NoSuchEventUserException;

@ControllerAdvice
public class EventUserExceptionHandler {

    @ExceptionHandler(NoSuchEventUserException.class)
    public ResponseEntity<String> handleNoSuchEventUserException(NoSuchEventUserException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
