package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.EventConflictException;
import ua.yatsergray.backend.v2.exception.NoSuchEventException;

@ControllerAdvice
public class EventExceptionHandler {

    @ExceptionHandler(EventConflictException.class)
    public ResponseEntity<String> handleEventConflictException(EventConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchEventException.class)
    public ResponseEntity<String> handleNoSuchEventException(NoSuchEventException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
