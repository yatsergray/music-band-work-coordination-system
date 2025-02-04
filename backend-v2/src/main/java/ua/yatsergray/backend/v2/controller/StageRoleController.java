package ua.yatsergray.backend.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.yatsergray.backend.v2.domain.dto.StageRoleDTO;
import ua.yatsergray.backend.v2.service.impl.StageRoleServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stage-roles")
public class StageRoleController {
    private final StageRoleServiceImpl stageRoleService;

    @Autowired
    public StageRoleController(StageRoleServiceImpl stageRoleService) {
        this.stageRoleService = stageRoleService;
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
}
