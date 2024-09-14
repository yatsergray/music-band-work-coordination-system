package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.BandAccessRoleAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandAccessRoleException;

@ControllerAdvice
public class BandAccessRoleExceptionHandler {

    @ExceptionHandler(BandAccessRoleAlreadyExistsException.class)
    public ResponseEntity<String> handleBandAccessRoleAlreadyExistsException(BandAccessRoleAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchBandAccessRoleException.class)
    public ResponseEntity<String> handleNoSuchBandAccessRoleException(NoSuchBandAccessRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
