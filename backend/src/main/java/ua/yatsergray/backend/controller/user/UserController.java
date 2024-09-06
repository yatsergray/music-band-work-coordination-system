package ua.yatsergray.backend.controller.user;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.dto.user.editable.UserEditableDTO;
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

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserEditableDTO userEditableDTO) {
        return ResponseEntity.ok(userService.addUser(userEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> readUserById(@PathVariable("id") UUID id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> readAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable("id") UUID id, @RequestBody UserEditableDTO userEditableDTO) {
        return ResponseEntity.ok(userService.modifyUserById(id, userEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") UUID id) {
        userService.removeUserById(id);

        return ResponseEntity.ok().build();
    }
}
