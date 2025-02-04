package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.NoSuchUserException;
import ua.yatsergray.backend.v2.exception.UserAlreadyExistsException;
import ua.yatsergray.backend.v2.exception.UserRoleConflictException;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleNoSuchUserException(NoSuchUserException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UserRoleConflictException.class)
    public ResponseEntity<String> handleUserRoleConflictException(UserRoleConflictException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
