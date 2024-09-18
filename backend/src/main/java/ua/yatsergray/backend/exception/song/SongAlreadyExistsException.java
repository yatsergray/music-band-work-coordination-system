package ua.yatsergray.backend.exception.song;

public class SongAlreadyExistsException extends Exception {

    public SongAlreadyExistsException(String message) {
        super(message);
    }
}
