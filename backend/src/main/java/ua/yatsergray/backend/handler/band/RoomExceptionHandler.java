package ua.yatsergray.backend.handler.band;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.band.RoomAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchRoomException;

@ControllerAdvice
public class RoomExceptionHandler {

    @ExceptionHandler(NoSuchRoomException.class)
    public ResponseEntity<String> handleNoSuchRoomException(NoSuchRoomException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RoomAlreadyExistsException.class)
    public ResponseEntity<String> handleRoomAlreadyExistsException(RoomAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
