package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.EventSongAlreadyExistsException;
import ua.yatsergray.backend.v2.exception.EventSongConflictException;
import ua.yatsergray.backend.v2.exception.NoSuchEventSongException;

@ControllerAdvice
public class EventSongExceptionHandler {

    @ExceptionHandler(EventSongConflictException.class)
    public ResponseEntity<String> handleEventSongConflictException(EventSongConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EventSongAlreadyExistsException.class)
    public ResponseEntity<String> handleEventSongAlreadyExistsException(EventSongAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchEventSongException.class)
    public ResponseEntity<String> handleNoSuchEventSongException(NoSuchEventSongException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
