package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandAccessRoleEditableDTO;
import ua.yatsergray.backend.exception.band.BandAccessRoleAlreadyExistsException;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandAccessRoleException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BandAccessRoleService {

    BandAccessRoleDTO addBandAccessRole(BandAccessRoleEditableDTO bandAccessRoleEditableDTO) throws BandAccessRoleAlreadyExistsException;

    Optional<BandAccessRoleDTO> getBandAccessRoleById(UUID bandAccessRoleId);

    List<BandAccessRoleDTO> getAllBandAccessRoles();

    BandAccessRoleDTO modifyBandAccessRoleById(UUID bandAccessRoleId, BandAccessRoleEditableDTO bandAccessRoleEditableDTO) throws NoSuchBandAccessRoleException, BandAccessRoleAlreadyExistsException;

    void removeBandAccessRoleById(UUID bandAccessRoleId) throws NoSuchBandAccessRoleException, ChildEntityExistsException;
}
