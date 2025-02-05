package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.EventUserAlreadyExistsException;
import ua.yatsergray.backend.v2.exception.EventUserConflictException;
import ua.yatsergray.backend.v2.exception.NoSuchEventUserException;

@ControllerAdvice
public class EventUserExceptionHandler {

    @ExceptionHandler(EventUserConflictException.class)
    public ResponseEntity<String> handleEventUserConflictException(EventUserConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EventUserAlreadyExistsException.class)
    public ResponseEntity<String> handleEventUserAlreadyExistsException(EventUserAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchEventUserException.class)
    public ResponseEntity<String> handleNoSuchEventUserException(NoSuchEventUserException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
