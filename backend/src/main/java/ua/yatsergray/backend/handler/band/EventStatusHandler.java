package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.EventStatusAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchEventStatusException;

@ControllerAdvice
public class EventStatusHandler {

    @ExceptionHandler(NoSuchEventStatusException.class)
    public ResponseEntity<String> handleNoSuchEventStatusException(NoSuchEventStatusException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EventStatusAlreadyExistsException.class)
    public ResponseEntity<String> handleEventStatusAlreadyExistsException(EventStatusAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
