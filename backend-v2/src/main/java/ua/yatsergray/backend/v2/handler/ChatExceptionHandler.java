package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.ChatAlreadyExistsException;
import ua.yatsergray.backend.v2.exception.ChatUserConflictException;
import ua.yatsergray.backend.v2.exception.NoSuchChatException;

@ControllerAdvice
public class ChatExceptionHandler {

    @ExceptionHandler(ChatAlreadyExistsException.class)
    public ResponseEntity<String> handleChatAlreadyExistsException(ChatAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchChatException.class)
    public ResponseEntity<String> handleNoSuchChatException(NoSuchChatException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ChatUserConflictException.class)
    public ResponseEntity<String> handleChatUserConflictException(ChatUserConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
