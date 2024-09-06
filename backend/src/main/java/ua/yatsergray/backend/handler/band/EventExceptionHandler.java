package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.NoSuchEventException;

@ControllerAdvice
public class EventExceptionHandler {

    @ExceptionHandler(NoSuchEventException.class)
    public ResponseEntity<String> handleNoSuchEventException(NoSuchEventException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
