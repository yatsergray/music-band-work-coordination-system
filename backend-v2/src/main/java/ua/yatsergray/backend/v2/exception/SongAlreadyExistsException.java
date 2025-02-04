package ua.yatsergray.backend.v2.exception;

public class SongAlreadyExistsException extends Exception {

    public SongAlreadyExistsException(String message) {
        super(message);
    }
}
