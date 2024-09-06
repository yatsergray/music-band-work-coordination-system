package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongStructureException;

@ControllerAdvice
public class SongStructureExceptionHandler {

    @ExceptionHandler(NoSuchSongStructureException.class)
    public ResponseEntity<String> handleNoSuchSongStructureException(NoSuchSongStructureException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
