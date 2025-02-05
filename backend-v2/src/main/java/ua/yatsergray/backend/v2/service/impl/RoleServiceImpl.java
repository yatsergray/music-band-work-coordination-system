package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.RoleDTO;
import ua.yatsergray.backend.v2.mapper.RoleMapper;
import ua.yatsergray.backend.v2.repository.RoleRepository;
import ua.yatsergray.backend.v2.service.RoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
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
    public Optional<RoleDTO> getRoleById(UUID roleId) {
        return roleRepository.findById(roleId).map(roleMapper::mapToRoleDTO);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleMapper.mapAllToRoleDTOList(roleRepository.findAll());
    }
}
