package ua.yatsergray.backend.service.user;

import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.dto.user.editable.UserEditableDTO;
import ua.yatsergray.backend.exception.user.NoSuchUserException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDTO addUser(UserEditableDTO userEditableDTO);

    Optional<UserDTO> getUserById(UUID id);

    List<UserDTO> getAllUsers();

    UserDTO modifyUserById(UUID id, UserEditableDTO userEditableDTO) throws NoSuchUserException;

    void removeUserById(UUID id) throws NoSuchUserException;
}
