package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.NoSuchInvitationException;

@ControllerAdvice
public class InvitationExceptionHandler {

    @ExceptionHandler(NoSuchInvitationException.class)
    public ResponseEntity<String> handleNoSuchInvitationException(NoSuchInvitationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
