package ua.yatsergray.backend.exception.user;

public class NoSuchUserException extends Exception {

    public NoSuchUserException(String message) {
        super(message);
    }
}
