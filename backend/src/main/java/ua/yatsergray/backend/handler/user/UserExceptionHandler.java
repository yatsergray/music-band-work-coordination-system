package ua.yatsergray.backend.handler.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.exception.user.UserAlreadyExistsException;
import ua.yatsergray.backend.exception.user.UserRoleConflictException;

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
