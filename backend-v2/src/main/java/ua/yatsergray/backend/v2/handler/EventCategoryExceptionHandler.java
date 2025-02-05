package ua.yatsergray.backend.v2.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.v2.exception.NoSuchEventCategoryException;

@ControllerAdvice
public class EventCategoryExceptionHandler {

    @ExceptionHandler(NoSuchEventCategoryException.class)
    public ResponseEntity<String> handleNoSuchEventCategoryException(NoSuchEventCategoryException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
