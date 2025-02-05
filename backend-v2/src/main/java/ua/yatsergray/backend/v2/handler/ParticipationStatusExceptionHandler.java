package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.NoSuchParticipationStatusException;

@ControllerAdvice
public class ParticipationStatusExceptionHandler {

    @ExceptionHandler(NoSuchParticipationStatusException.class)
    public ResponseEntity<String> handleNoSuchParticipationStatusException(NoSuchParticipationStatusException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
