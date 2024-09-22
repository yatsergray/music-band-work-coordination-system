package ua.yatsergray.backend.exception.user;

public class UserRoleConflictException extends Exception {

    public UserRoleConflictException(String message) {
        super(message);
    }
}
