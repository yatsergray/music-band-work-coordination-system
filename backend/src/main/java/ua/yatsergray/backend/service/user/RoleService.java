package ua.yatsergray.backend.service.user;

import ua.yatsergray.backend.domain.dto.user.RoleDTO;
import ua.yatsergray.backend.domain.dto.user.editable.RoleEditableDTO;
import ua.yatsergray.backend.exception.user.NoSuchRoleException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {

    RoleDTO addRole(RoleEditableDTO roleEditableDTO);

    Optional<RoleDTO> getRoleById(UUID id);

    List<RoleDTO> getAllRoles();

    RoleDTO modifyRoleById(UUID id, RoleEditableDTO roleEditableDTO) throws NoSuchRoleException;

    void removeRoleById(UUID id) throws NoSuchRoleException;
}
