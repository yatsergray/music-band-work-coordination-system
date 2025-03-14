package ua.yatsergray.backend.controller.user;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.user.RoleDTO;
import ua.yatsergray.backend.domain.request.user.RoleCreateRequest;
import ua.yatsergray.backend.domain.request.user.RoleUpdateRequest;
import ua.yatsergray.backend.service.user.impl.RoleServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleServiceImpl roleService;

    @Autowired
    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleCreateRequest roleCreateRequest) {
        return ResponseEntity.ok(roleService.addRole(roleCreateRequest));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> readRoleById(@PathVariable("roleId") UUID roleId) {
        return roleService.getRoleById(roleId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> readAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @SneakyThrows
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDTO> updateRoleById(@PathVariable("roleId") UUID roleId, @Valid @RequestBody RoleUpdateRequest roleUpdateRequest) {
        return ResponseEntity.ok(roleService.modifyRoleById(roleId, roleUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable("roleId") UUID roleId) {
        roleService.removeRoleById(roleId);

        return ResponseEntity.ok().build();
    }
}
