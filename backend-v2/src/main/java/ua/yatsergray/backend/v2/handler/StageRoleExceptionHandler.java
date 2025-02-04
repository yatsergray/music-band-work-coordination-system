package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.NoSuchStageRoleException;

@ControllerAdvice
public class StageRoleExceptionHandler {

    @ExceptionHandler(NoSuchStageRoleException.class)
    public ResponseEntity<String> handleNoSuchStageRoleException(NoSuchStageRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
