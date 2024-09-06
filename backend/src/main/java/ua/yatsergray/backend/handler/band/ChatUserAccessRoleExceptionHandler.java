package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.NoSuchChatUserAccessRoleException;

@ControllerAdvice
public class ChatUserAccessRoleExceptionHandler {

    @ExceptionHandler(NoSuchChatUserAccessRoleException.class)
    public ResponseEntity<String> handleChatUserAccessRoleException(NoSuchChatUserAccessRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}