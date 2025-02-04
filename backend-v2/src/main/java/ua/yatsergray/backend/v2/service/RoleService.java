package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.RoleDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {

    Optional<RoleDTO> getRoleById(UUID roleId);

    List<RoleDTO> getAllRoles();
}
