package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.NoSuchSongException;
import ua.yatsergray.backend.v2.exception.SongAlreadyExistsException;

@ControllerAdvice
public class SongExceptionHandler {

    @ExceptionHandler(SongAlreadyExistsException.class)
    public ResponseEntity<String> handleSongAlreadyExistsException(SongAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchSongException.class)
    public ResponseEntity<String> handleNoSuchSongException(NoSuchSongException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
