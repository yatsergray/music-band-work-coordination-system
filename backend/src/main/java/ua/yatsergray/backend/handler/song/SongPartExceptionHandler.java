package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;

@ControllerAdvice
public class SongPartExceptionHandler {

    @ExceptionHandler(NoSuchSongPartException.class)
    public ResponseEntity<String> handleNoSuchSongPartException(NoSuchSongPartException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
