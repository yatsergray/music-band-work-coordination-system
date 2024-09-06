package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchTimeSignatureException;

@ControllerAdvice
public class TimeSignatureExceptionHandler {

    @ExceptionHandler(NoSuchTimeSignatureException.class)
    public ResponseEntity<String> handleNoSuchTimeSignatureException(NoSuchTimeSignatureException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
