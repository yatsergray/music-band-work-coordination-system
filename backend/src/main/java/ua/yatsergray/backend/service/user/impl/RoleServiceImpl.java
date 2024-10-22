package ua.yatsergray.backend.service.user.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.user.RoleDTO;
import ua.yatsergray.backend.domain.dto.user.editable.RoleEditableDTO;
import ua.yatsergray.backend.domain.entity.user.Role;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.user.NoSuchRoleException;
import ua.yatsergray.backend.exception.user.RoleAlreadyExistsException;
import ua.yatsergray.backend.mapper.user.RoleMapper;
import ua.yatsergray.backend.repository.user.RoleRepository;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.user.RoleService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, RoleRepository roleRepository, UserRepository userRepository) {
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RoleDTO addRole(RoleEditableDTO roleEditableDTO) throws RoleAlreadyExistsException {
        return roleMapper.mapToRoleDTO(roleRepository.save(configureRole(new Role(), roleEditableDTO)));
    }

    @Override
    public Optional<RoleDTO> getRoleById(UUID roleId) {
        return roleRepository.findById(roleId).map(roleMapper::mapToRoleDTO);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleMapper.mapAllToRoleDTOList(roleRepository.findAll());
    }

    @Override
    public RoleDTO modifyRoleById(UUID roleId, RoleEditableDTO roleEditableDTO) throws NoSuchRoleException, RoleAlreadyExistsException {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchRoleException(String.format("Role with id=\"%s\" does not exist", roleId)));

        return roleMapper.mapToRoleDTO(roleRepository.save(configureRole(role, roleEditableDTO)));
    }

    @Override
    public void removeRoleById(UUID roleId) throws NoSuchRoleException, ChildEntityExistsException {
        if (!roleRepository.existsById(roleId)) {
            throw new NoSuchRoleException(String.format("Role with id=\"%s\" does not exist", roleId));
        }

        checkIfRoleHasChildEntity(roleId);

        roleRepository.deleteById(roleId);
    }

    private Role configureRole(Role role, RoleEditableDTO roleEditableDTO) throws RoleAlreadyExistsException {
        if (Objects.isNull(role.getId())) {
            if (roleRepository.existsByName(roleEditableDTO.getName())) {
                throw new RoleAlreadyExistsException(String.format("Role with name=\"%s\" already exists", roleEditableDTO.getName()));
            }

            if (roleRepository.existsByType(roleEditableDTO.getType())) {
                throw new RoleAlreadyExistsException(String.format("Role with type=\"%s\" already exists", roleEditableDTO.getType()));
            }
        } else {
            if (!roleEditableDTO.getName().equals(role.getName()) && roleRepository.existsByName(roleEditableDTO.getName())) {
                throw new RoleAlreadyExistsException(String.format("Role with name=\"%s\" already exists", roleEditableDTO.getName()));
            }

            if (!roleEditableDTO.getType().equals(role.getType()) && roleRepository.existsByType(roleEditableDTO.getType())) {
                throw new RoleAlreadyExistsException(String.format("Role with type=\"%s\" already exists", roleEditableDTO.getType()));
            }
        }

        role.setName(roleEditableDTO.getName());
        role.setType(roleEditableDTO.getType());

        return role;
    }

    private void checkIfRoleHasChildEntity(UUID roleId) throws ChildEntityExistsException {
        long roleChildEntityAmount = userRepository.countByRolesId(roleId);

        if (roleChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d User(s) depend(s) on the Role with id=%s", roleChildEntityAmount, roleId));
        }
    }
}
