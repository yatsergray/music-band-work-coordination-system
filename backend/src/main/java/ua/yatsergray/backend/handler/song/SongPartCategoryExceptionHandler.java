package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;

@ControllerAdvice
public class SongPartCategoryExceptionHandler {

    @ExceptionHandler(NoSuchSongPartCategoryException.class)
    public ResponseEntity<String> handleNoSuchSongPartCategoryException(NoSuchSongPartCategoryException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
