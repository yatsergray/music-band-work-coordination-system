package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;

@ControllerAdvice
public class StageRoleExceptionHandler {

    @ExceptionHandler(NoSuchStageRoleException.class)
    public ResponseEntity<String> handleNoSuchStageRoleException(NoSuchStageRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
