package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.MusicBandUserDTO;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserAccessRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserStageRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.type.ParticipationStatusType;
import ua.yatsergray.backend.v2.service.InvitationService;
import ua.yatsergray.backend.v2.service.MusicBandUserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/music-band-users")
public class MusicBandUserController {
    private final MusicBandUserService musicBandUserService;
    private final InvitationService invitationService;

    @Autowired
    public MusicBandUserController(MusicBandUserService musicBandUserService, InvitationService invitationService) {
        this.musicBandUserService = musicBandUserService;
        this.invitationService = invitationService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<MusicBandUserDTO> createMusicBandUser(@Valid @RequestBody MusicBandUserCreateRequest musicBandUserCreateRequest) {
        return ResponseEntity.ok(musicBandUserService.addMusicBandUser(musicBandUserCreateRequest));
    }

    @SneakyThrows
    @GetMapping("/music-band/{musicBandId}/user/{userId}")
    public ResponseEntity<MusicBandUserDTO> readMusicBandUserByMusicBandIdAndUserId(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId) {
        return musicBandUserService.getMusicBandUserByMusicBandIdAndUserId(musicBandId, userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @SneakyThrows
    @GetMapping("/music-band/{musicBandId}")
    public ResponseEntity<Page<MusicBandUserDTO>> readAllMusicBandUsersByMusicBandIdAndPageAndSize(@PathVariable("musicBandId") UUID musicBandId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(musicBandUserService.getAllMusicBandUsersByMusicBandIdAndPageAndSize(musicBandId, page, size));
    }

    @SneakyThrows
    @PostMapping("/music-band/{musicBandId}/user/{userId}/music-band-access-role")
    public ResponseEntity<MusicBandUserDTO> createMusicBandUserAccessRole(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId, @Valid @RequestBody MusicBandUserAccessRoleCreateRequest musicBandUserAccessRoleCreateRequest) {
        return ResponseEntity.ok(musicBandUserService.addMusicBandAccessRoleToMusicBandUserByMusicBandIdAndUserId(musicBandId, userId, musicBandUserAccessRoleCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/music-band/{musicBandId}/user/{userId}/music-band-access-role/{musicBandAccessRoleId}")
    public ResponseEntity<Void> deleteMusicBandUserAccessRole(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId, @PathVariable("musicBandAccessRoleId") UUID musicBandAccessRoleId) {
        musicBandUserService.removeMusicBandAccessRoleFromMusicBandUserByMusicBandIdAndUserId(musicBandId, userId, musicBandAccessRoleId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/music-band/{musicBandId}/user/{userId}/stage-role")
    public ResponseEntity<MusicBandUserDTO> createMusicBandUserStageRole(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId, @Valid @RequestBody MusicBandUserStageRoleCreateRequest musicBandUserStageRoleCreateRequest) {
        return ResponseEntity.ok(musicBandUserService.addStageRoleToMusicBandUserByMusicBandIdAndUserId(musicBandId, userId, musicBandUserStageRoleCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/music-band/{musicBandId}/user/{userId}/stage-role/{stageRoleId}")
    public ResponseEntity<Void> deleteMusicBandUserStageRole(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId, @PathVariable("stageRoleId") UUID stageRoleId) {
        musicBandUserService.removeStageRoleFromMusicBandUserByMusicBandIdAndUserId(musicBandId, userId, stageRoleId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @GetMapping("/join")
    public ResponseEntity<MusicBandUserDTO> joinUserToMusicBand(@RequestParam("invitationToken") String invitationToken) {
        // TODO: Check if invitation token was created for authenticated user

        MusicBandUserDTO musicBandUserDTO = musicBandUserService.addMusicBandUserByInvitationToken(invitationToken);

        invitationService.changeInvitationParticipationStatusByInvitationToken(invitationToken, ParticipationStatusType.ACCEPTED);

        return ResponseEntity.ok(musicBandUserDTO);
    }

    @SneakyThrows
    @DeleteMapping("/music-band/{musicBandId}/user/{userId}")
    public ResponseEntity<Void> deleteMusicBandUserByMusicBandIdAndUserId(@PathVariable("musicBandId") UUID musicBandId, @PathVariable("userId") UUID userId) {
        musicBandUserService.removeMusicBandUserByMusicBandIdAndUserId(musicBandId, userId);

        return ResponseEntity.ok().build();
    }
}
