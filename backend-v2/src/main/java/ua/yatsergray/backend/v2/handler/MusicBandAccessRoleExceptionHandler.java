package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.NoSuchMusicBandAccessRoleException;

@ControllerAdvice
public class MusicBandAccessRoleExceptionHandler {

    @ExceptionHandler(NoSuchMusicBandAccessRoleException.class)
    public ResponseEntity<String> handleNoSuchMusicBandAccessRoleException(NoSuchMusicBandAccessRoleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
