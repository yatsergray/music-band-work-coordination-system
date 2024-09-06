package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.NoSuchBandUserStageRoleException;

@ControllerAdvice
public class BandUserStageRoleExceptionHandler {

    @ExceptionHandler(NoSuchBandUserStageRoleException.class)
    public ResponseEntity<String> handleNoSuchBandUserStageRoleException(NoSuchBandUserStageRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
