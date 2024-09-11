package ua.yatsergray.backend.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.dto.user.editable.UserEditableDTO;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.mapper.user.UserMapper;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.user.UserService;

import java.util.List;
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
    public UserDTO addUser(UserEditableDTO userEditableDTO) {
        User user = User.builder()
                .firstName(userEditableDTO.getFirstName())
                .lastName(userEditableDTO.getLastName())
                .email(userEditableDTO.getEmail())
                .password(userEditableDTO.getPassword())
                .build();

        return userMapper.mapToUserDTO(userRepository.save(user));
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
    public UserDTO modifyUserById(UUID userId, UserEditableDTO userEditableDTO) throws NoSuchUserException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User does not exist with id=%s", userId)));

        user.setFirstName(userEditableDTO.getFirstName());
        user.setLastName(userEditableDTO.getLastName());
        user.setEmail(userEditableDTO.getEmail());
        user.setPassword(userEditableDTO.getPassword());

        return userMapper.mapToUserDTO(userRepository.save(user));
    }

    @Override
    public void removeUserById(UUID userId) throws NoSuchUserException {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchUserException(String.format("User does not exist with id=%s", userId));
        }

        userRepository.deleteById(userId);
    }
}
