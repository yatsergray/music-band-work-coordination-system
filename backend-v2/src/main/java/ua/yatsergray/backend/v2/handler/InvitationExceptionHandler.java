package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.InvitationAlreadyExistsException;
import ua.yatsergray.backend.v2.exception.InvitationConflictException;
import ua.yatsergray.backend.v2.exception.NoSuchInvitationException;

@ControllerAdvice
public class InvitationExceptionHandler {

    @ExceptionHandler(InvitationConflictException.class)
    public ResponseEntity<String> handleInvitationConflictException(InvitationConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(InvitationAlreadyExistsException.class)
    public ResponseEntity<String> handleInvitationAlreadyExistsException(InvitationAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchInvitationException.class)
    public ResponseEntity<String> handleNoSuchInvitationException(NoSuchInvitationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
