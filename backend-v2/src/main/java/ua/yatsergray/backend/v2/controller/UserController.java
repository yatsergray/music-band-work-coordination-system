package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.UserDTO;
import ua.yatsergray.backend.v2.domain.request.UserCreateRequest;
import ua.yatsergray.backend.v2.domain.request.UserRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.request.UserUpdateRequest;
import ua.yatsergray.backend.v2.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(userService.addUser(userCreateRequest));
    }

    @SneakyThrows
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> readUserById(@PathVariable("userId") UUID userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> readAllUsersByPageAndSize(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getAllUsersByPageAndSize(page, size));
    }

    @GetMapping("/music-band/{musicBandId}")
    public ResponseEntity<Page<UserDTO>> readAllUsersByMusicBandIdPageAndSize(@PathVariable("musicBandId") UUID musicBandId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getAllUsersByMusicBandIdAndPageAndSize(musicBandId, page, size));
    }

    @SneakyThrows
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable("userId") UUID userId, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userService.modifyUserById(userId, userUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") UUID userId) {
        userService.removeUserById(userId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/{userId}/roles")
    public ResponseEntity<UserDTO> createUserRole(@PathVariable("userId") UUID userId, @Valid @RequestBody UserRoleCreateRequest userRoleCreateRequest) {
        return ResponseEntity.ok(userService.addUserRole(userId, userRoleCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable("userId") UUID userId, @PathVariable("roleId") UUID roleId) {
        userService.removeUserRole(userId, roleId);

        return ResponseEntity.ok().build();
    }
}
