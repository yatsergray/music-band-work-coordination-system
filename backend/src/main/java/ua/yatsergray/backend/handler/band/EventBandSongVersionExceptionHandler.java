package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.NoSuchEventBandSongVersionException;

@ControllerAdvice
public class EventBandSongVersionExceptionHandler {

    @ExceptionHandler(NoSuchEventBandSongVersionException.class)
    public ResponseEntity<String> handleEventBandSongVersionException(NoSuchEventBandSongVersionException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
