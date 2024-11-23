package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.dto.band.BandUserDTO;
import ua.yatsergray.backend.domain.request.band.BandCreateUpdateRequest;
import ua.yatsergray.backend.domain.request.band.BandUserAccessRoleCreateRequest;
import ua.yatsergray.backend.domain.request.band.BandUserCreateRequest;
import ua.yatsergray.backend.domain.request.band.BandUserStageRoleCreateRequest;
import ua.yatsergray.backend.service.band.impl.BandServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bands")
public class BandController {
    private final BandServiceImpl bandService;

    @Autowired
    public BandController(BandServiceImpl bandService) {
        this.bandService = bandService;
    }

    @PostMapping
    public ResponseEntity<BandDTO> createBand(@Valid @RequestBody BandCreateUpdateRequest bandCreateUpdateRequest) {
        return ResponseEntity.ok(bandService.addBand(bandCreateUpdateRequest));
    }

    @GetMapping("/{bandId}")
    public ResponseEntity<BandDTO> readBandById(@PathVariable("bandId") UUID bandId) {
        return bandService.getBandById(bandId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BandDTO>> readAllBands() {
        return ResponseEntity.ok(bandService.getAllBands());
    }

    @SneakyThrows
    @PutMapping("/{bandId}")
    public ResponseEntity<BandDTO> updateBandById(@PathVariable("bandId") UUID bandId, @Valid @RequestBody BandCreateUpdateRequest bandCreateUpdateRequest) {
        return ResponseEntity.ok(bandService.modifyBandById(bandId, bandCreateUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{bandId}")
    public ResponseEntity<Void> deleteBandById(@PathVariable("bandId") UUID bandId) {
        bandService.removeBandById(bandId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/{bandId}/users")
    public ResponseEntity<BandUserDTO> createBandUser(@PathVariable("bandId") UUID bandId, @Valid @RequestBody BandUserCreateRequest bandUserCreateRequest) {
        return ResponseEntity.ok(bandService.addBandUser(bandId, bandUserCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{bandId}/users/{userId}")
    public ResponseEntity<Void> deleteBandUser(@PathVariable("bandId") UUID bandId, @PathVariable("userId") UUID userId) {
        bandService.removeBandUser(bandId, userId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/{bandId}/users/{userId}/band-access-roles")
    public ResponseEntity<BandUserDTO> createBandUserAccessRole(@PathVariable("bandId") UUID bandId, @PathVariable("userId") UUID userId, @Valid @RequestBody BandUserAccessRoleCreateRequest bandUserAccessRoleCreateRequest) {
        return ResponseEntity.ok(bandService.addBandUserAccessRole(bandId, userId, bandUserAccessRoleCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{bandId}/users/{userId}/band-access-roles/{bandAccessRoleId}")
    public ResponseEntity<Void> deleteBandUserAccessRole(@PathVariable("bandId") UUID bandId, @PathVariable("userId") UUID userId, @PathVariable("bandAccessRoleId") UUID bandAccessRoleId) {
        bandService.removeBandUserAccessRole(bandId, userId, bandAccessRoleId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/{bandId}/users/{userId}/stage-roles")
    public ResponseEntity<BandUserDTO> createBandUserStageRole(@PathVariable("bandId") UUID bandId, @PathVariable("userId") UUID userId, @Valid @RequestBody BandUserStageRoleCreateRequest bandUserStageRoleCreateRequest) {
        return ResponseEntity.ok(bandService.addBandUserStageRole(bandId, userId, bandUserStageRoleCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{bandId}/users/{userId}/stage-roles/{stageRoleId}")
    public ResponseEntity<Void> deleteBandUserStageRole(@PathVariable("bandId") UUID bandId, @PathVariable("userId") UUID userId, @PathVariable("stageRoleId") UUID stageRoleId) {
        bandService.removeBandUserStageRole(bandId, userId, stageRoleId);

        return ResponseEntity.ok().build();
    }
}
