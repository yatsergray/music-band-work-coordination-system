package ua.yatsergray.backend.service.user;

import ua.yatsergray.backend.domain.dto.user.RoleDTO;
import ua.yatsergray.backend.domain.request.user.RoleCreateRequest;
import ua.yatsergray.backend.domain.request.user.RoleUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.user.NoSuchRoleException;
import ua.yatsergray.backend.exception.user.RoleAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {

    RoleDTO addRole(RoleCreateRequest roleCreateRequest) throws RoleAlreadyExistsException;

    Optional<RoleDTO> getRoleById(UUID roleId);

    List<RoleDTO> getAllRoles();

    RoleDTO modifyRoleById(UUID roleId, RoleUpdateRequest roleUpdateRequest) throws NoSuchRoleException, RoleAlreadyExistsException;

    void removeRoleById(UUID roleId) throws NoSuchRoleException, ChildEntityExistsException;
}
