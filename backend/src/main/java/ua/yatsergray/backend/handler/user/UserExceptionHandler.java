package ua.yatsergray.backend.handler.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.user.NoSuchUserException;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleNoSuchUserException(NoSuchUserException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
