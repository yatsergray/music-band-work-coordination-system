package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongPartDetailsException;
import ua.yatsergray.backend.exception.song.SongPartDetailsConflictException;

@ControllerAdvice
public class SongPartDetailsExceptionHandler {

    @ExceptionHandler(SongPartDetailsConflictException.class)
    public ResponseEntity<String> handleSongPartDetailsConflictException(SongPartDetailsConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchSongPartDetailsException.class)
    public ResponseEntity<String> handleNoSuchSongPartDetailsException(NoSuchSongPartDetailsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
