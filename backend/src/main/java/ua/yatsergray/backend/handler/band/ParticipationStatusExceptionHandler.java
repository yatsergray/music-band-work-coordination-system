package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.NoSuchParticipationStatusException;

@ControllerAdvice
public class ParticipationStatusExceptionHandler {

    @ExceptionHandler(NoSuchParticipationStatusException.class)
    public ResponseEntity<String> handleNoSuchParticipationStatusException(NoSuchParticipationStatusException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
