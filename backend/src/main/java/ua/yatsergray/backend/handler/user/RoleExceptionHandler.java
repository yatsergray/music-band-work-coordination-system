package ua.yatsergray.backend.handler.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.user.NoSuchRoleException;
import ua.yatsergray.backend.exception.user.RoleAlreadyExistsException;

@ControllerAdvice
public class RoleExceptionHandler {

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<String> handleRoleAlreadyExistsException(RoleAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchRoleException.class)
    public ResponseEntity<String> handleNoSuchRoleException(NoSuchRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
