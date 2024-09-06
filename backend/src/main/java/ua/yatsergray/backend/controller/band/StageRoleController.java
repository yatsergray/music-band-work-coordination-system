package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.StageRoleEditableDTO;
import ua.yatsergray.backend.service.band.impl.StageRoleServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stage-roles")
public class StageRoleController {
    private final StageRoleServiceImpl stageRoleService;

    @Autowired
    public StageRoleController(StageRoleServiceImpl stageRoleService) {
        this.stageRoleService = stageRoleService;
    }

    @PostMapping
    public ResponseEntity<StageRoleDTO> createStageRole(@RequestBody StageRoleEditableDTO stageRoleEditableDTO) {
        return ResponseEntity.ok(stageRoleService.addStageRole(stageRoleEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StageRoleDTO> readStageRoleById(@PathVariable("id") UUID id) {
        return stageRoleService.getStageRoleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<StageRoleDTO>> readAllStageRoles() {
        return ResponseEntity.ok(stageRoleService.getAllStageRoles());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<StageRoleDTO> updateStageRoleById(@PathVariable("id") UUID id, @RequestBody StageRoleEditableDTO stageRoleEditableDTO) {
        return ResponseEntity.ok(stageRoleService.modifyStageRoleById(id, stageRoleEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStageRoleById(@PathVariable("id") UUID id) {
        stageRoleService.removeStageRoleById(id);

        return ResponseEntity.ok().build();
    }
}
