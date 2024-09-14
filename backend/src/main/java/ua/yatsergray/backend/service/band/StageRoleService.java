package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.StageRoleEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.band.StageRoleAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StageRoleService {

    StageRoleDTO addStageRole(StageRoleEditableDTO stageRoleEditableDTO) throws StageRoleAlreadyExistsException;

    Optional<StageRoleDTO> getStageRoleById(UUID stageRoleId);

    List<StageRoleDTO> getAllStageRoles();

    StageRoleDTO modifyStageRoleById(UUID stageRoleId, StageRoleEditableDTO stageRoleEditableDTO) throws NoSuchStageRoleException, StageRoleAlreadyExistsException;

    void removeStageRoleById(UUID stageRoleId) throws NoSuchStageRoleException;
}
