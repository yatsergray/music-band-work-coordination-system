package ua.yatsergray.backend.service.user;

import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.request.user.UserCreateRequest;
import ua.yatsergray.backend.domain.request.user.UserRoleCreateRequest;
import ua.yatsergray.backend.domain.request.user.UserUpdateRequest;
import ua.yatsergray.backend.exception.user.NoSuchRoleException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.exception.user.UserAlreadyExistsException;
import ua.yatsergray.backend.exception.user.UserRoleConflictException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDTO addUser(UserCreateRequest userCreateRequest) throws UserAlreadyExistsException, NoSuchRoleException;

    Optional<UserDTO> getUserById(UUID userId);

    List<UserDTO> getAllUsers();

    UserDTO modifyUserById(UUID userId, UserUpdateRequest userUpdateRequest) throws NoSuchUserException;

    void removeUserById(UUID id) throws NoSuchUserException;

    UserDTO addUserRole(UUID userId, UserRoleCreateRequest userRoleCreateRequest) throws NoSuchUserException, NoSuchRoleException, UserRoleConflictException;

    void removeUserRole(UUID userId, UUID roleId) throws NoSuchUserException, NoSuchRoleException, UserRoleConflictException;
}
