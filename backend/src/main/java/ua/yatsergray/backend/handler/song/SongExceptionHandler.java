package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongException;

@ControllerAdvice
public class SongExceptionHandler {

    @ExceptionHandler(NoSuchSongException.class)
    public ResponseEntity<String> handleNoSuchSongException(NoSuchSongException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
