package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongMoodException;
import ua.yatsergray.backend.exception.song.SongMoodAlreadyExistsException;

@ControllerAdvice
public class SongMoodExceptionHandler {

    @ExceptionHandler(NoSuchSongMoodException.class)
    public ResponseEntity<String> handleNoSuchSongMoodException(NoSuchSongMoodException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(SongMoodAlreadyExistsException.class)
    public ResponseEntity<String> handleSongMoodAlreadyExistsException(SongMoodAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
