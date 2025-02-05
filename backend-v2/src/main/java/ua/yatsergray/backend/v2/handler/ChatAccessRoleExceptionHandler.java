package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.NoSuchChatAccessRoleException;

@ControllerAdvice
public class ChatAccessRoleExceptionHandler {

    @ExceptionHandler(NoSuchChatAccessRoleException.class)
    public ResponseEntity<String> handleNoSuchChatAccessRoleException(NoSuchChatAccessRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
