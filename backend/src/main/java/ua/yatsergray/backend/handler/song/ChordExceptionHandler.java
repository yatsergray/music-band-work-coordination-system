package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.ChordAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchChordException;

@ControllerAdvice
public class ChordExceptionHandler {

    @ExceptionHandler(ChordAlreadyExistsException.class)
    public ResponseEntity<String> handleChordAlreadyExistsException(ChordAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchChordException.class)
    public ResponseEntity<String> handleNoSuchChordException(NoSuchChordException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
