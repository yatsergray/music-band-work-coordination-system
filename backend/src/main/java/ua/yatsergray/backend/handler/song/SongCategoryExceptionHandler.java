package ua.yatsergray.backend.handler.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.yatsergray.backend.exception.song.NoSuchSongCategoryException;
import ua.yatsergray.backend.exception.song.SongCategoryAlreadyExistsException;

@ControllerAdvice
public class SongCategoryExceptionHandler {

    @ExceptionHandler(NoSuchSongCategoryException.class)
    public ResponseEntity<String> handleNoSuchSongCategoryException(NoSuchSongCategoryException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(SongCategoryAlreadyExistsException.class)
    public ResponseEntity<String> handleSongCategoryAlreadyExistsException(SongCategoryAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
