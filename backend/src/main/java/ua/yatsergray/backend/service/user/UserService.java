package ua.yatsergray.backend.service.user;

import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.dto.user.editable.UserEditableDTO;
import ua.yatsergray.backend.domain.dto.user.editable.UserRoleEditableDTO;
import ua.yatsergray.backend.exception.user.NoSuchRoleException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.exception.user.UserAlreadyExistsException;
import ua.yatsergray.backend.exception.user.UserRoleConflictException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDTO addUser(UserEditableDTO userEditableDTO) throws UserAlreadyExistsException, NoSuchRoleException;

    Optional<UserDTO> getUserById(UUID userId) throws NoSuchUserException;

    List<UserDTO> getAllUsers();

    UserDTO modifyUserById(UUID userId, UserEditableDTO userEditableDTO) throws NoSuchUserException, UserAlreadyExistsException;

    void removeUserById(UUID userId) throws NoSuchUserException;

    UserDTO addUserRole(UUID userId, UserRoleEditableDTO userRoleEditableDTO) throws NoSuchUserException, NoSuchRoleException, UserRoleConflictException;

    void removeUserRole(UUID userId, UUID roleId) throws NoSuchUserException, NoSuchRoleException, UserRoleConflictException;
}
