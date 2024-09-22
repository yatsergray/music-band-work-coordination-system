package ua.yatsergray.backend.controller.user;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.dto.user.editable.UserEditableDTO;
import ua.yatsergray.backend.domain.dto.user.editable.UserRoleEditableDTO;
import ua.yatsergray.backend.service.user.impl.UserServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserEditableDTO userEditableDTO) {
        return ResponseEntity.ok(userService.addUser(userEditableDTO));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> readUserById(@PathVariable("userId") UUID userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> readAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @SneakyThrows
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable("userId") UUID userId, @Valid @RequestBody UserEditableDTO userEditableDTO) {
        return ResponseEntity.ok(userService.modifyUserById(userId, userEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") UUID userId) {
        userService.removeUserById(userId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/create-user-role")
    public ResponseEntity<UserDTO> createUserRole(@Valid @RequestBody UserRoleEditableDTO userRoleEditableDTO) {
        return ResponseEntity.ok(userService.addUserRole(userRoleEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/delete-user-role")
    public ResponseEntity<UserDTO> deleteUserRole(@Valid @RequestBody UserRoleEditableDTO userRoleEditableDTO) {
        return ResponseEntity.ok(userService.removeUserRole(userRoleEditableDTO));
    }
}
