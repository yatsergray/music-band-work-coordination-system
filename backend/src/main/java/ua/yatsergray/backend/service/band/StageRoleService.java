package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.StageRoleEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StageRoleService {

    StageRoleDTO addStageRole(StageRoleEditableDTO stageRoleEditableDTO);

    Optional<StageRoleDTO> getStageRoleById(UUID id);

    List<StageRoleDTO> getAllStageRoles();

    StageRoleDTO modifyStageRoleById(UUID id, StageRoleEditableDTO stageRoleEditableDTO) throws NoSuchStageRoleException;

    void removeStageRoleById(UUID id) throws NoSuchStageRoleException;
}
