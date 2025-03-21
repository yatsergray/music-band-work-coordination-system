package ua.yatsergray.backend.v2.service;

import org.springframework.data.domain.Page;
import ua.yatsergray.backend.v2.domain.dto.MusicBandDTO;
import ua.yatsergray.backend.v2.domain.dto.MusicBandUserDTO;
import ua.yatsergray.backend.v2.domain.request.MusicBandCreateUpdateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserAccessRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserStageRoleCreateRequest;
import ua.yatsergray.backend.v2.exception.*;

import java.util.Optional;
import java.util.UUID;

public interface MusicBandService {

    MusicBandDTO addMusicBand(MusicBandCreateUpdateRequest musicBandCreateUpdateRequest) throws MusicBandAlreadyExists;

    Optional<MusicBandDTO> getMusicBandById(UUID musicBandId);

//    List<MusicBandDTO> getAllMusicBands();

    Page<MusicBandDTO> getAllMusicBandsByPageAndSize(int page, int size);

    MusicBandDTO modifyMusicBandById(UUID musicBandId, MusicBandCreateUpdateRequest musicBandCreateUpdateRequest) throws NoSuchMusicBandException, MusicBandAlreadyExists;

    void removeMusicBandById(UUID musicBandId) throws NoSuchMusicBandException;

    MusicBandUserDTO addMusicBandUser(UUID musicBandId, MusicBandUserCreateRequest musicBandUserCreateRequest) throws NoSuchMusicBandException, NoSuchUserException, NoSuchMusicBandAccessRoleException, MusicBandUserConflictException;

    MusicBandUserDTO addMusicBandUserByInvitationToken(String invitationToken) throws NoSuchInvitationException, InvalidInvitationException, NoSuchUserException, MusicBandUserConflictException, NoSuchMusicBandException, NoSuchMusicBandAccessRoleException, NoSuchParticipationStatusException;

    void removeMusicBandUser(UUID musicBandId, UUID userId) throws NoSuchMusicBandException, NoSuchUserException, MusicBandUserConflictException;

    MusicBandUserDTO addMusicBandUserAccessRole(UUID musicBandId, UUID userId, MusicBandUserAccessRoleCreateRequest musicBandUserAccessRoleCreateRequest) throws NoSuchMusicBandException, NoSuchUserException, NoSuchMusicBandAccessRoleException, MusicBandUserConflictException;

    void removeMusicBandUserAccessRole(UUID musicBandId, UUID userId, UUID musicBandAccessRoleId) throws NoSuchMusicBandException, NoSuchUserException, NoSuchMusicBandAccessRoleException, MusicBandUserConflictException;

    MusicBandUserDTO addMusicBandUserStageRole(UUID musicBandId, UUID userId, MusicBandUserStageRoleCreateRequest musicBandUserStageRoleCreateRequest) throws NoSuchMusicBandException, NoSuchUserException, NoSuchStageRoleException, MusicBandUserConflictException;

    void removeMusicBandUserStageRole(UUID musicBandId, UUID userId, UUID stageRoleId) throws NoSuchMusicBandException, NoSuchUserException, NoSuchStageRoleException, MusicBandUserConflictException;
}
