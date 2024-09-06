package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongPartStructureDetailsException;

@ControllerAdvice
public class SongPartStructureDetailsExceptionHandler {

    @ExceptionHandler(NoSuchSongPartStructureDetailsException.class)
    public ResponseEntity<String> handleNoSuchSongPartStructureDetailsException(NoSuchSongPartStructureDetailsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
