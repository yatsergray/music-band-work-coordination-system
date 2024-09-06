package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongPartKeyChordException;

@ControllerAdvice
public class SongPartKeyChordExceptionHandler {

    @ExceptionHandler(NoSuchSongPartKeyChordException.class)
    public ResponseEntity<String> handleNoSuchSongPartKeyChordException(NoSuchSongPartKeyChordException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
