package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.KeyAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;

@ControllerAdvice
public class KeyExceptionHandler {

    @ExceptionHandler(KeyAlreadyExistsException.class)
    public ResponseEntity<String> handleKeyAlreadyExistsException(KeyAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchKeyException.class)
    public ResponseEntity<String> handleNoSuchKeyException(NoSuchKeyException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
