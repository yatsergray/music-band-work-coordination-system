package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.BandSongVersionConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandSongVersionException;

@ControllerAdvice
public class BandSongVersionExceptionHandler {

    @ExceptionHandler(BandSongVersionConflictException.class)
    public ResponseEntity<String> handleBandSongVersionConflictException(BandSongVersionConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchBandSongVersionException.class)
    public ResponseEntity<String> handleNoSuchBandSongVersionException(NoSuchBandSongVersionException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
