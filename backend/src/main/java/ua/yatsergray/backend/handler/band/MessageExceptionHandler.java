package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.MessageConflictException;
import ua.yatsergray.backend.exception.band.NoSuchMessageException;

@ControllerAdvice
public class MessageExceptionHandler {

    @ExceptionHandler(MessageConflictException.class)
    public ResponseEntity<String> handleMessageConflictException(MessageConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity<String> handleNoSuchMessageException(NoSuchMessageException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
