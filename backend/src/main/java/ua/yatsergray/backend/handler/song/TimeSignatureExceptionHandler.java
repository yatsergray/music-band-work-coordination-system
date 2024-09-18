package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchTimeSignatureException;
import ua.yatsergray.backend.exception.song.TimeSignatureAlreadyExistsException;

@ControllerAdvice
public class TimeSignatureExceptionHandler {

    @ExceptionHandler(TimeSignatureAlreadyExistsException.class)
    public ResponseEntity<String> handleTimeSignatureAlreadyExistsException(TimeSignatureAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchTimeSignatureException.class)
    public ResponseEntity<String> handleNoSuchTimeSignatureException(NoSuchTimeSignatureException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
