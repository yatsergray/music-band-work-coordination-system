package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.BandUserConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;

@ControllerAdvice
public class BandExceptionHandler {

    @ExceptionHandler(NoSuchBandException.class)
    public ResponseEntity<String> handleNoSuchBandException(NoSuchBandException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BandUserConflictException.class)
    public ResponseEntity<String> handleBandUserConflictException(BandUserConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
