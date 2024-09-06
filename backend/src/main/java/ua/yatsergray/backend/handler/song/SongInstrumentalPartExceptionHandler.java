package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongInstrumentalPartException;

@ControllerAdvice
public class SongInstrumentalPartExceptionHandler {

    @ExceptionHandler(NoSuchSongInstrumentalPartException.class)
    public ResponseEntity<String> handleNoSuchSongInstrumentalPartException(NoSuchSongInstrumentalPartException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
