package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.*;
import ua.yatsergray.backend.v2.domain.request.*;
import ua.yatsergray.backend.v2.domain.type.ParticipationStatusType;
import ua.yatsergray.backend.v2.exception.InvalidInvitationException;
import ua.yatsergray.backend.v2.exception.NoSuchInvitationException;
import ua.yatsergray.backend.v2.exception.NoSuchParticipationStatusException;
import ua.yatsergray.backend.v2.exception.NoSuchUserException;
import ua.yatsergray.backend.v2.service.impl.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/music-bands")
public class MusicBandController {
    private final MusicBandServiceImpl musicBandService;
    private final InvitationServiceImpl invitationServiceImpl;
    private final JwtServiceImpl jwtServiceImpl;
    private final ParticipationStatusServiceImpl participationStatusServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public MusicBandController(MusicBandServiceImpl musicBandService, InvitationServiceImpl invitationServiceImpl, JwtServiceImpl jwtServiceImpl, ParticipationStatusServiceImpl participationStatusServiceImpl, UserServiceImpl userServiceImpl) {
        this.musicBandService = musicBandService;
        this.invitationServiceImpl = invitationServiceImpl;
        this.jwtServiceImpl = jwtServiceImpl;
        this.participationStatusServiceImpl = participationStatusServiceImpl;
        this.userServiceImpl = userServiceImpl;
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

    @SneakyThrows
    @GetMapping("/invite")
    public ResponseEntity<MusicBandUserDTO> inviteUserToMusicBand(@RequestParam("token") String token) {
        InvitationDTO invitationDTO = invitationServiceImpl.getInvitationByToken(token)
                .orElseThrow(() -> new NoSuchInvitationException(String.format("Invitation with token=\"%s\" does not exist", token)));

        if (!jwtServiceImpl.isUserToMusicBandInvitationTokenValid(token, invitationDTO.getEmail(), invitationDTO.getMusicBandId())) {
            throw new InvalidInvitationException(String.format("Invitation token=\"%s\" is invalid", token));
        }

        ParticipationStatusDTO participationStatusDTO = participationStatusServiceImpl.getParticipationStatusByType(ParticipationStatusType.ACCEPTED)
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status with type=\"%s\" does not exist", ParticipationStatusType.ACCEPTED)));
        UserDTO userDTO = userServiceImpl.getUserByEmail(invitationDTO.getEmail())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with email=\"%s\" does not exist", invitationDTO.getEmail())));

        InvitationUpdateRequest invitationUpdateRequest = InvitationUpdateRequest.builder()
                .participationStatusId(participationStatusDTO.getId())
                .build();
        MusicBandUserCreateRequest musicBandUserCreateRequest = MusicBandUserCreateRequest.builder()
                .userId(userDTO.getId())
                .build();

        invitationServiceImpl.modifyInvitationById(invitationDTO.getId(), invitationUpdateRequest);

        return ResponseEntity.ok(musicBandService.addMusicBandUser(invitationDTO.getMusicBandId(), musicBandUserCreateRequest));
    }
}
