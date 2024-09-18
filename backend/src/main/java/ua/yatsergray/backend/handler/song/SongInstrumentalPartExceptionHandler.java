package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongInstrumentalPartException;
import ua.yatsergray.backend.exception.song.SongInstrumentalPartAlreadyExistsException;

@ControllerAdvice
public class SongInstrumentalPartExceptionHandler {

    @ExceptionHandler(SongInstrumentalPartAlreadyExistsException.class)
    public ResponseEntity<String> handleSongInstrumentalPartAlreadyExistsException(SongInstrumentalPartAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchSongInstrumentalPartException.class)
    public ResponseEntity<String> handleNoSuchSongInstrumentalPartException(NoSuchSongInstrumentalPartException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
