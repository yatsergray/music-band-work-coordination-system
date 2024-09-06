package ua.yatsergray.backend.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.user.RoleDTO;
import ua.yatsergray.backend.domain.dto.user.editable.RoleEditableDTO;
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
        return roleMapper.mapToRoleDTO(roleRepository.save(roleMapper.mapToRole(roleEditableDTO)));
    }

    @Override
    public Optional<RoleDTO> getRoleById(UUID id) {
        return roleRepository.findById(id).map(roleMapper::mapToRoleDTO);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleMapper.mapAllToRoleDTOList(roleRepository.findAll());
    }

    @Override
    public RoleDTO modifyRoleById(UUID id, RoleEditableDTO roleEditableDTO) throws NoSuchRoleException {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setName(roleEditableDTO.getName());
                    role.setType(roleEditableDTO.getType());

                    return roleMapper.mapToRoleDTO(roleRepository.save(role));
                })
                .orElseThrow(() -> new NoSuchRoleException(String.format("Role does not exist with id=%s", id)));
    }

    @Override
    public void removeRoleById(UUID id) throws NoSuchRoleException {
        if (!roleRepository.existsById(id)) {
            throw new NoSuchRoleException(String.format("Role does not exist with id=%s", id));
        }

        roleRepository.deleteById(id);
    }
}
