package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandAccessRoleEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchBandAccessRoleException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BandAccessRoleService {

    BandAccessRoleDTO addBandAccessRole(BandAccessRoleEditableDTO bandAccessRoleEditableDTO);

    Optional<BandAccessRoleDTO> getBandAccessRoleById(UUID id);

    List<BandAccessRoleDTO> getAllBandAccessRoles();

    BandAccessRoleDTO modifyBandAccessRoleById(UUID id, BandAccessRoleEditableDTO bandAccessRoleEditableDTO) throws NoSuchBandAccessRoleException;

    void removeBandAccessRoleById(UUID id) throws NoSuchBandAccessRoleException;
}
