package ua.yatsergray.backend.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.ChildEntityExistsException;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(ChildEntityExistsException.class)
    public ResponseEntity<String> handleChildEntityExistsException(ChildEntityExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
