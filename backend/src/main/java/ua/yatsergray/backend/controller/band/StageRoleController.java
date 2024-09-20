package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
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

    @SneakyThrows
    @PostMapping
    public ResponseEntity<StageRoleDTO> createStageRole(@Valid @RequestBody StageRoleEditableDTO stageRoleEditableDTO) {
        return ResponseEntity.ok(stageRoleService.addStageRole(stageRoleEditableDTO));
    }

    @GetMapping("/{stageRoleId}")
    public ResponseEntity<StageRoleDTO> readStageRoleById(@PathVariable("stageRoleId") UUID stageRoleId) {
        return stageRoleService.getStageRoleById(stageRoleId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<StageRoleDTO>> readAllStageRoles() {
        return ResponseEntity.ok(stageRoleService.getAllStageRoles());
    }

    @SneakyThrows
    @PutMapping("/{stageRoleId}")
    public ResponseEntity<StageRoleDTO> updateStageRoleById(@PathVariable("stageRoleId") UUID stageRoleId, @Valid @RequestBody StageRoleEditableDTO stageRoleEditableDTO) {
        return ResponseEntity.ok(stageRoleService.modifyStageRoleById(stageRoleId, stageRoleEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{stageRoleId}")
    public ResponseEntity<Void> deleteStageRoleById(@PathVariable("stageRoleId") UUID stageRoleId) {
        stageRoleService.removeStageRoleById(stageRoleId);

        return ResponseEntity.ok().build();
    }
}
