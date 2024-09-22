package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.SongAlreadyExistsException;
import ua.yatsergray.backend.exception.song.SongKeyConflictException;

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

    @ExceptionHandler(SongKeyConflictException.class)
    public ResponseEntity<String> handleSongKeyConflictException(SongKeyConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
