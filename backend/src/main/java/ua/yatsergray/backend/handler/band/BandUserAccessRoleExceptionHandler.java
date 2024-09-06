package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.NoSuchBandUserAccessRoleException;

@ControllerAdvice
public class BandUserAccessRoleExceptionHandler {

    @ExceptionHandler(NoSuchBandUserAccessRoleException.class)
    public ResponseEntity<String> handleNoSuchBandUserAccessRoleException(NoSuchBandUserAccessRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
