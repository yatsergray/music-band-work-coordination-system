package ua.yatsergray.backend.service.user.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.entity.user.Role;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.domain.request.user.UserCreateRequest;
import ua.yatsergray.backend.domain.request.user.UserRoleCreateRequest;
import ua.yatsergray.backend.domain.request.user.UserUpdateRequest;
import ua.yatsergray.backend.domain.type.user.RoleType;
import ua.yatsergray.backend.exception.user.NoSuchRoleException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.exception.user.UserAlreadyExistsException;
import ua.yatsergray.backend.exception.user.UserRoleConflictException;
import ua.yatsergray.backend.mapper.user.UserMapper;
import ua.yatsergray.backend.repository.user.RoleRepository;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDTO addUser(UserCreateRequest userCreateRequest) throws UserAlreadyExistsException, NoSuchRoleException {
        Role role = roleRepository.findByType(RoleType.USER)
                .orElseThrow(() -> new NoSuchRoleException(String.format("Role with type=\"%s\" does not exist", RoleType.USER)));

        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new UserAlreadyExistsException(String.format("User with email=\"%s\" already exists", userCreateRequest.getEmail()));
        }

        User user = User.builder()
                .firstName(userCreateRequest.getFirstName())
                .lastName(userCreateRequest.getLastName())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword())
                .build();

        user.getRoles().add(role);

        return UserMapper.INSTANCE.mapToUserDTO(userRepository.save(user));
    }

    @Override
    public Optional<UserDTO> getUserById(UUID userId) {
        return userRepository.findById(userId).map(UserMapper.INSTANCE::mapToUserDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return UserMapper.INSTANCE.mapAllToUserDTOList(userRepository.findAll());
    }

    @Override
    public UserDTO modifyUserById(UUID userId, UserUpdateRequest userUpdateRequest) throws NoSuchUserException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId)));

        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());

        return UserMapper.INSTANCE.mapToUserDTO(userRepository.save(user));
    }

    @Override
    public void removeUserById(UUID id) throws NoSuchUserException {
        if (!userRepository.existsById(id)) {
            throw new NoSuchUserException(String.format("User with id=\"%s\" does not exist", id));
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserDTO addUserRole(UUID userId, UserRoleCreateRequest userRoleCreateRequest) throws NoSuchUserException, NoSuchRoleException, UserRoleConflictException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId)));
        Role role = roleRepository.findById(userRoleCreateRequest.getRoleId())
                .orElseThrow(() -> new NoSuchRoleException(String.format("Role with id=\"%s\" does not exist", userRoleCreateRequest.getRoleId())));

        if (user.getRoles().contains(role)) {
            throw new UserRoleConflictException(String.format("User with id=\"%s\" already has role with id=\"%s\"", userId, userRoleCreateRequest.getRoleId()));
        }

        user.getRoles().add(role);

        return UserMapper.INSTANCE.mapToUserDTO(userRepository.save(user));
    }

    @Override
    public void removeUserRole(UUID userId, UUID roleId) throws NoSuchUserException, NoSuchRoleException, UserRoleConflictException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId)));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchRoleException(String.format("Role with id=\"%s\" does not exist", roleId)));

        if (!user.getRoles().contains(role)) {
            throw new UserRoleConflictException(String.format("User with id=\"%s\" does not have role with id=\"%s\"", userId, roleId));
        }

        user.getRoles().remove(role);

        userRepository.save(user);
    }
}
