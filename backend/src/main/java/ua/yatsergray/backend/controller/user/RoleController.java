package ua.yatsergray.backend.controller.user;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.user.RoleDTO;
import ua.yatsergray.backend.domain.dto.user.editable.RoleEditableDTO;
import ua.yatsergray.backend.service.user.impl.RoleServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleServiceImpl roleService;

    @Autowired
    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleEditableDTO roleEditableDTO) {
        return ResponseEntity.ok(roleService.addRole(roleEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> readRoleById(@PathVariable("id") UUID id) {
        return roleService.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> readAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRoleById(@PathVariable("id") UUID id, @RequestBody RoleEditableDTO roleEditableDTO) {
        return ResponseEntity.ok(roleService.modifyRoleById(id, roleEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable("id") UUID id) {
        roleService.removeRoleById(id);

        return ResponseEntity.ok().build();
    }
}
