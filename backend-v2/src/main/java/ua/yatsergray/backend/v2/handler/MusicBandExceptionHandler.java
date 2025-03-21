package ua.yatsergray.backend.v2.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.MusicBandAlreadyExists;
import ua.yatsergray.backend.v2.exception.MusicBandUserConflictException;
import ua.yatsergray.backend.v2.exception.NoSuchMusicBandException;

@ControllerAdvice
public class MusicBandExceptionHandler {

    @ExceptionHandler(MusicBandAlreadyExists.class)
    public ResponseEntity<String> handleMusicBandAlreadyExists(MusicBandAlreadyExists e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(NoSuchMusicBandException.class)
    public ResponseEntity<String> handleNoSuchMusicBandException(NoSuchMusicBandException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MusicBandUserConflictException.class)
    public ResponseEntity<String> handleMusicBandUserConflictException(MusicBandUserConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
