package ua.yatsergray.backend.exception.user;

public class RoleAlreadyExistsException extends Exception {

    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
