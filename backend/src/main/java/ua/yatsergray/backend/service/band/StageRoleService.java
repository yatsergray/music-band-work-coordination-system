package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.request.band.StageRoleCreateRequest;
import ua.yatsergray.backend.domain.request.band.StageRoleUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.band.StageRoleAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StageRoleService {

    StageRoleDTO addStageRole(StageRoleCreateRequest stageRoleCreateRequest) throws StageRoleAlreadyExistsException;

    Optional<StageRoleDTO> getStageRoleById(UUID stageRoleId);

    List<StageRoleDTO> getAllStageRoles();

    StageRoleDTO modifyStageRoleById(UUID stageRoleId, StageRoleUpdateRequest stageRoleUpdateRequest) throws NoSuchStageRoleException, StageRoleAlreadyExistsException;

    void removeStageRoleById(UUID stageRoleId) throws NoSuchStageRoleException, ChildEntityExistsException;
}
