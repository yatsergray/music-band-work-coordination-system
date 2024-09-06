package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandAccessRoleEditableDTO;
import ua.yatsergray.backend.service.band.impl.BandAccessRoleServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/band-access-roles")
public class BandAccessRoleController {
    private final BandAccessRoleServiceImpl bandAccessRoleService;

    @Autowired
    public BandAccessRoleController(BandAccessRoleServiceImpl bandAccessRoleService) {
        this.bandAccessRoleService = bandAccessRoleService;
    }

    @PostMapping
    public ResponseEntity<BandAccessRoleDTO> createBandAccessRole(@RequestBody BandAccessRoleEditableDTO bandAccessRoleEditableDTO) {
        return ResponseEntity.ok(bandAccessRoleService.addBandAccessRole(bandAccessRoleEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandAccessRoleDTO> readBandAccessRoleById(@PathVariable("id") UUID id) {
        return bandAccessRoleService.getBandAccessRoleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BandAccessRoleDTO>> readAllBandAccessRoles() {
        return ResponseEntity.ok(bandAccessRoleService.getAllBandAccessRoles());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<BandAccessRoleDTO> updateBandAccessRoleById(@PathVariable("id") UUID id, @RequestBody BandAccessRoleEditableDTO bandAccessRoleEditableDTO) {
        return ResponseEntity.ok(bandAccessRoleService.modifyBandAccessRoleById(id, bandAccessRoleEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBandAccessRoleById(@PathVariable("id") UUID id) {
        bandAccessRoleService.removeBandAccessRoleById(id);

        return ResponseEntity.ok().build();
    }
}
