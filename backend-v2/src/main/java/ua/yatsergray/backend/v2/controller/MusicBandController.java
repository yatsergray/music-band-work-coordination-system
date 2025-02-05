package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.MusicBandDTO;
import ua.yatsergray.backend.v2.domain.dto.MusicBandUserDTO;
import ua.yatsergray.backend.v2.domain.request.MusicBandCreateUpdateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserAccessRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserStageRoleCreateRequest;
import ua.yatsergray.backend.v2.service.impl.MusicBandServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/music-bands")
public class MusicBandController {
    private final MusicBandServiceImpl musicBandService;

    @Autowired
    public MusicBandController(MusicBandServiceImpl musicBandService) {
        this.musicBandService = musicBandService;
    }

    @PostMapping
    public ResponseEntity<MusicBandDTO> createMusicBand(@Valid @RequestBody MusicBandCreateUpdateRequest musicBandCreateUpdateRequest) {
        return ResponseEntity.ok(musicBandService.addMusicBand(musicBandCreateUpdateRequest));
    }

    @GetMapping("/{musicBandId}")
    public ResponseEntity<MusicBandDTO> readMusicBandById(@PathVariable("musicBandId") UUID musicBandId) {
        return musicBandService.getMusicBandById(musicBandId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MusicBandDTO>> readAllMusicBands() {
        return ResponseEntity.ok(musicBandService.getAllMusicBands());
    }

    @SneakyThrows
    @PutMapping("/{musicBandId}")
    public ResponseEntity<MusicBandDTO> updateMusicBandById(@PathVariable("musicBandId") UUID musicBandId, @Valid @RequestBody MusicBandCreateUpdateRequest musicBandCreateUpdateRequest) {
        return ResponseEntity.ok(musicBandService.modifyMusicBandById(musicBandId, musicBandCreateUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{musicBandId}")
    public ResponseEntity<Void> deleteMusicBandById(@PathVariable("musicBandId") UUID musicBandId) {
        musicBandService.removeMusicBandById(musicBandId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/{musicBandId}/users")
    public ResponseEntity<MusicBandUserDTO> createMusicBandUser(@PathVariable("musicBandId") UUID musicBandId, @Valid @RequestBody MusicBandUserCreateRequest musicBandUserCreateRequest) {
        return ResponseEntity.ok(musicBandService.addMusicBandUser(musicBandId, musicBandUserCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{musicBandId}/users/{userId}")
    public ResponseEntity<Void> deleteMusicBandUser(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId) {
        musicBandService.removeMusicBandUser(musicBandId, userId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/{musicBandId}/users/{userId}/music-band-access-roles")
    public ResponseEntity<MusicBandUserDTO> createMusicBandUserAccessRole(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId, @Valid @RequestBody MusicBandUserAccessRoleCreateRequest musicBandUserAccessRoleCreateRequest) {
        return ResponseEntity.ok(musicBandService.addMusicBandUserAccessRole(musicBandId, userId, musicBandUserAccessRoleCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{musicBandId}/users/{userId}/music-band-access-roles/{musicBandAccessRoleId}")
    public ResponseEntity<Void> deleteMusicBandUserAccessRole(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId, @PathVariable("musicBandAccessRoleId") UUID musicBandAccessRoleId) {
        musicBandService.removeMusicBandUserAccessRole(musicBandId, userId, musicBandAccessRoleId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/{musicBandId}/users/{userId}/stage-roles")
    public ResponseEntity<MusicBandUserDTO> createMusicBandUserStageRole(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId, @Valid @RequestBody MusicBandUserStageRoleCreateRequest musicBandUserStageRoleCreateRequest) {
        return ResponseEntity.ok(musicBandService.addMusicBandUserStageRole(musicBandId, userId, musicBandUserStageRoleCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{musicBandId}/users/{userId}/stage-roles/{stageRoleId}")
    public ResponseEntity<Void> deleteMusicBandUserStageRole(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId, @PathVariable("stageRoleId") UUID stageRoleId) {
        musicBandService.removeMusicBandUserStageRole(musicBandId, userId, stageRoleId);

        return ResponseEntity.ok().build();
    }
}
