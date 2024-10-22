package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.dto.band.BandUserDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandUserAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandUserEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandUserStageRoleEditableDTO;
import ua.yatsergray.backend.exception.band.BandUserConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandAccessRoleException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BandService {

    BandDTO addBand(BandEditableDTO bandEditableDTO);

    Optional<BandDTO> getBandById(UUID bandId);

    List<BandDTO> getAllBands();

    BandDTO modifyBandById(UUID bandId, BandEditableDTO bandEditableDTO) throws NoSuchBandException;

    void removeBandById(UUID bandId) throws NoSuchBandException;

    BandUserDTO addBandUser(UUID bandId, BandUserEditableDTO bandUserEditableDTO) throws NoSuchBandException, NoSuchUserException, NoSuchBandAccessRoleException, BandUserConflictException;

    void removeBandUser(UUID bandId, UUID userId) throws NoSuchBandException, NoSuchUserException, BandUserConflictException;

    BandUserDTO addBandUserAccessRole(UUID bandId, UUID userId, BandUserAccessRoleEditableDTO bandUserAccessRoleEditableDTO) throws NoSuchBandException, NoSuchUserException, NoSuchBandAccessRoleException, BandUserConflictException;

    void removeBandUserAccessRole(UUID bandId, UUID userId, UUID bandAccessRoleId) throws NoSuchBandException, NoSuchUserException, NoSuchBandAccessRoleException, BandUserConflictException;

    BandUserDTO addBandUserStageRole(UUID bandId, UUID userId, BandUserStageRoleEditableDTO bandUserStageRoleEditableDTO) throws NoSuchBandException, NoSuchUserException, NoSuchStageRoleException, BandUserConflictException;

    void removeBandUserStageRole(UUID bandId, UUID userId, UUID stageRoleId) throws NoSuchBandException, NoSuchUserException, NoSuchStageRoleException, BandUserConflictException;
}
