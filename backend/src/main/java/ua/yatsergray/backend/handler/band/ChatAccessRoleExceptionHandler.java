package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.NoSuchChatAccessRoleException;

@ControllerAdvice
public class ChatAccessRoleExceptionHandler {

    @ExceptionHandler(NoSuchChatAccessRoleException.class)
    public ResponseEntity<String> handleChatAccessRoleException(NoSuchChatAccessRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
