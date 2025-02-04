package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.StageRoleDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StageRoleService {

    Optional<StageRoleDTO> getStageRoleById(UUID stageRoleId);

    List<StageRoleDTO> getAllStageRoles();
}
