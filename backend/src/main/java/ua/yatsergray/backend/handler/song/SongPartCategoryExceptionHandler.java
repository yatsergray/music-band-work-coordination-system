package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;
import ua.yatsergray.backend.exception.song.SongPartCategoryAlreadyExistsException;

@ControllerAdvice
public class SongPartCategoryExceptionHandler {

    @ExceptionHandler(SongPartCategoryAlreadyExistsException.class)
    public ResponseEntity<String> handleSongPartCategoryAlreadyExistsException(SongPartCategoryAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchSongPartCategoryException.class)
    public ResponseEntity<String> handleNoSuchSongPartCategoryException(NoSuchSongPartCategoryException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
