package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchArtistException;

@ControllerAdvice
public class ArtistExceptionHandler {

    @ExceptionHandler(NoSuchArtistException.class)
    public ResponseEntity<String> handleNoSuchArtistException(NoSuchArtistException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
