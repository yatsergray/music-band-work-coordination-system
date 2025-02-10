package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.UserDTO;
import ua.yatsergray.backend.v2.domain.request.UserCreateRequest;
import ua.yatsergray.backend.v2.domain.request.UserRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.request.UserUpdateRequest;
import ua.yatsergray.backend.v2.exception.NoSuchRoleException;
import ua.yatsergray.backend.v2.exception.NoSuchUserException;
import ua.yatsergray.backend.v2.exception.UserAlreadyExistsException;
import ua.yatsergray.backend.v2.exception.UserRoleConflictException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDTO addUser(UserCreateRequest userCreateRequest) throws NoSuchRoleException, UserAlreadyExistsException;

    Optional<UserDTO> getUserById(UUID userId);

    Optional<UserDTO> getUserByEmail(String userEmail);

    List<UserDTO> getAllUsers();

    UserDTO modifyUserById(UUID userId, UserUpdateRequest userUpdateRequest) throws NoSuchUserException;

    void removeUserById(UUID id) throws NoSuchUserException;

    UserDTO addUserRole(UUID userId, UserRoleCreateRequest userRoleCreateRequest) throws NoSuchUserException, NoSuchRoleException, UserRoleConflictException;

    void removeUserRole(UUID userId, UUID roleId) throws NoSuchUserException, NoSuchRoleException, UserRoleConflictException;
}
