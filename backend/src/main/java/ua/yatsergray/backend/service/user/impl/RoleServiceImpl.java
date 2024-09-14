package ua.yatsergray.backend.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.user.RoleDTO;
import ua.yatsergray.backend.domain.dto.user.editable.RoleEditableDTO;
import ua.yatsergray.backend.domain.entity.user.Role;
import ua.yatsergray.backend.exception.user.NoSuchRoleException;
import ua.yatsergray.backend.mapper.user.RoleMapper;
import ua.yatsergray.backend.repository.user.RoleRepository;
import ua.yatsergray.backend.service.user.RoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, RoleRepository roleRepository) {
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDTO addRole(RoleEditableDTO roleEditableDTO) {
        Role role = Role.builder()
                .name(roleEditableDTO.getName())
                .type(roleEditableDTO.getType())
                .build();

        return roleMapper.mapToRoleDTO(roleRepository.save(role));
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
    public RoleDTO modifyRoleById(UUID roleId, RoleEditableDTO roleEditableDTO) throws NoSuchRoleException {
        return roleRepository.findById(roleId)
                .map(role -> {
                    role.setName(roleEditableDTO.getName());
                    role.setType(roleEditableDTO.getType());

                    return roleMapper.mapToRoleDTO(roleRepository.save(role));
                })
                .orElseThrow(() -> new NoSuchRoleException(String.format("Role with id=%s does not exist", roleId)));
    }

    @Override
    public void removeRoleById(UUID roleId) throws NoSuchRoleException {
        if (!roleRepository.existsById(roleId)) {
            throw new NoSuchRoleException(String.format("Role with id=%s does not exist", roleId));
        }

        roleRepository.deleteById(roleId);
    }
}
