package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.EventBandSongVersionConflictException;
import ua.yatsergray.backend.exception.band.NoSuchEventBandSongVersionException;

@ControllerAdvice
public class EventBandSongVersionExceptionHandler {

    @ExceptionHandler(EventBandSongVersionConflictException.class)
    public ResponseEntity<String> handleEventBandSongVersionConflictException(EventBandSongVersionConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchEventBandSongVersionException.class)
    public ResponseEntity<String> handleNoSuchEventBandSongVersionException(NoSuchEventBandSongVersionException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
