package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.ChatAccessRoleAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchChatAccessRoleException;

@ControllerAdvice
public class ChatAccessRoleExceptionHandler {

    @ExceptionHandler(ChatAccessRoleAlreadyExistsException.class)
    public ResponseEntity<String> handleChatAccessRoleAlreadyExistsException(ChatAccessRoleAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchChatAccessRoleException.class)
    public ResponseEntity<String> handleNoSuchChatAccessRoleException(NoSuchChatAccessRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
