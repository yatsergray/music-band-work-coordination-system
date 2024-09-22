package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongPartKeyChordException;
import ua.yatsergray.backend.exception.song.SongPartKeyChordAlreadyExistsException;
import ua.yatsergray.backend.exception.song.SongPartKeyChordConflictException;

@ControllerAdvice
public class SongPartKeyChordExceptionHandler {

    @ExceptionHandler(SongPartKeyChordConflictException.class)
    public ResponseEntity<String> handleSongPartKeyChordConflictException(SongPartKeyChordConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(SongPartKeyChordAlreadyExistsException.class)
    public ResponseEntity<String> handleSongPartKeyChordAlreadyExistsException(SongPartKeyChordAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchSongPartKeyChordException.class)
    public ResponseEntity<String> handleNoSuchSongPartKeyChordException(NoSuchSongPartKeyChordException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
