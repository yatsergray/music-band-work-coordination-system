package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.request.band.BandAccessRoleCreateRequest;
import ua.yatsergray.backend.domain.request.band.BandAccessRoleUpdateRequest;
import ua.yatsergray.backend.service.band.impl.BandAccessRoleServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/band-access-roles")
public class BandAccessRoleController {
    private final BandAccessRoleServiceImpl bandAccessRoleService;

    @Autowired
    public BandAccessRoleController(BandAccessRoleServiceImpl bandAccessRoleService) {
        this.bandAccessRoleService = bandAccessRoleService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<BandAccessRoleDTO> createBandAccessRole(@Valid @RequestBody BandAccessRoleCreateRequest bandAccessRoleCreateRequest) {
        return ResponseEntity.ok(bandAccessRoleService.addBandAccessRole(bandAccessRoleCreateRequest));
    }

    @GetMapping("/{bandAccessRoleId}")
    public ResponseEntity<BandAccessRoleDTO> readBandAccessRoleById(@PathVariable("bandAccessRoleId") UUID bandAccessRoleId) {
        return bandAccessRoleService.getBandAccessRoleById(bandAccessRoleId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BandAccessRoleDTO>> readAllBandAccessRoles() {
        return ResponseEntity.ok(bandAccessRoleService.getAllBandAccessRoles());
    }

    @SneakyThrows
    @PutMapping("/{bandAccessRoleId}")
    public ResponseEntity<BandAccessRoleDTO> updateBandAccessRoleById(@PathVariable("bandAccessRoleId") UUID bandAccessRoleId, @Valid @RequestBody BandAccessRoleUpdateRequest bandAccessRoleUpdateRequest) {
        return ResponseEntity.ok(bandAccessRoleService.modifyBandAccessRoleById(bandAccessRoleId, bandAccessRoleUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{bandAccessRoleId}")
    public ResponseEntity<Void> deleteBandAccessRoleById(@PathVariable("bandAccessRoleId") UUID bandAccessRoleId) {
        bandAccessRoleService.removeBandAccessRoleById(bandAccessRoleId);

        return ResponseEntity.ok().build();
    }
}
