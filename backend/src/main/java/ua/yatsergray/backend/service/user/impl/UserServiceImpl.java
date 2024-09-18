package ua.yatsergray.backend.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.dto.user.editable.UserEditableDTO;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.exception.user.UserAlreadyExistsException;
import ua.yatsergray.backend.mapper.user.UserMapper;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.user.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO addUser(UserEditableDTO userEditableDTO) throws UserAlreadyExistsException {
        return userMapper.mapToUserDTO(userRepository.save(configureUser(new User(), userEditableDTO)));
    }

    @Override
    public Optional<UserDTO> getUserById(UUID userId) {
        return userRepository.findById(userId).map(userMapper::mapToUserDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.mapAllToUserDTOList(userRepository.findAll());
    }

    @Override
    public UserDTO modifyUserById(UUID userId, UserEditableDTO userEditableDTO) throws NoSuchUserException, UserAlreadyExistsException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=%s does not exist", userId)));

        return userMapper.mapToUserDTO(userRepository.save(configureUser(user, userEditableDTO)));
    }

    @Override
    public void removeUserById(UUID id) throws NoSuchUserException {
        if (!userRepository.existsById(id)) {
            throw new NoSuchUserException(String.format("User with id=%s does not exist", id));
        }

        userRepository.deleteById(id);
    }

    private User configureUser(User user, UserEditableDTO userEditableDTO) throws UserAlreadyExistsException {
        if (Objects.isNull(user.getId())) {
            if (userRepository.existsByEmail(userEditableDTO.getEmail())) {
                throw new UserAlreadyExistsException(String.format("User with email=%s already exists", userEditableDTO.getEmail()));
            }
        } else {
            if (!userEditableDTO.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(userEditableDTO.getEmail())) {
                throw new UserAlreadyExistsException(String.format("User with email=%s already exists", userEditableDTO.getEmail()));
            }
        }

        user.setFirstName(userEditableDTO.getFirstName());
        user.setLastName(userEditableDTO.getLastName());
        user.setEmail(userEditableDTO.getEmail());
        user.setPassword(userEditableDTO.getPassword());

        return user;
    }
}
