package ua.yatsergray.backend.v2.service;

import org.springframework.data.domain.Page;
import ua.yatsergray.backend.v2.domain.dto.MusicBandUserDTO;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserAccessRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserStageRoleCreateRequest;
import ua.yatsergray.backend.v2.exception.*;

import java.util.Optional;
import java.util.UUID;

public interface MusicBandUserService {

    MusicBandUserDTO addMusicBandUser(MusicBandUserCreateRequest musicBandUserCreateRequest) throws NoSuchMusicBandException, NoSuchUserException, NoSuchMusicBandAccessRoleException, MusicBandUserConflictException;

    MusicBandUserDTO addMusicBandUserByInvitationToken(String invitationToken) throws NoSuchInvitationException, InvalidInvitationException, NoSuchUserException, MusicBandUserConflictException, NoSuchMusicBandException, NoSuchMusicBandAccessRoleException;

    Optional<MusicBandUserDTO> getMusicBandUserByMusicBandIdAndUserId(UUID musicBandId, UUID userId) throws NoSuchMusicBandException, NoSuchUserException, MusicBandUserConflictException;

    Page<MusicBandUserDTO> getAllMusicBandUsersByMusicBandIdAndPageAndSize(UUID musicBandId, int page, int size) throws NoSuchMusicBandException;

    MusicBandUserDTO addMusicBandAccessRoleToMusicBandUserByMusicBandIdAndUserId(UUID musicBandId, UUID userId, MusicBandUserAccessRoleCreateRequest musicBandUserAccessRoleCreateRequest) throws NoSuchMusicBandException, NoSuchUserException, NoSuchMusicBandAccessRoleException, MusicBandUserConflictException;

    void removeMusicBandAccessRoleFromMusicBandUserByMusicBandIdAndUserId(UUID musicBandId, UUID userId, UUID musicBandAccessRoleId) throws NoSuchMusicBandException, NoSuchUserException, NoSuchMusicBandAccessRoleException, MusicBandUserConflictException;

    MusicBandUserDTO addStageRoleToMusicBandUserByMusicBandIdAndUserId(UUID musicBandId, UUID userId, MusicBandUserStageRoleCreateRequest musicBandUserStageRoleCreateRequest) throws NoSuchMusicBandException, NoSuchUserException, NoSuchStageRoleException, MusicBandUserConflictException;

    void removeStageRoleFromMusicBandUserByMusicBandIdAndUserId(UUID musicBandId, UUID userId, UUID stageRoleId) throws NoSuchMusicBandException, NoSuchUserException, NoSuchStageRoleException, MusicBandUserConflictException;

    void removeMusicBandUserByMusicBandIdAndUserId(UUID musicBandId, UUID userId) throws NoSuchMusicBandException, NoSuchUserException, MusicBandUserConflictException;
}
