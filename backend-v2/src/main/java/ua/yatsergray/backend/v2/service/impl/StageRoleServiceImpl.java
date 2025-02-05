package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.StageRoleDTO;
import ua.yatsergray.backend.v2.mapper.StageRoleMapper;
import ua.yatsergray.backend.v2.repository.StageRoleRepository;
import ua.yatsergray.backend.v2.service.StageRoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class StageRoleServiceImpl implements StageRoleService {
    private final StageRoleRepository stageRoleRepository;

    @Autowired
    public StageRoleServiceImpl(StageRoleRepository stageRoleRepository) {
        this.stageRoleRepository = stageRoleRepository;
    }

    @Override
    public Optional<StageRoleDTO> getStageRoleById(UUID stageRoleId) {
        return stageRoleRepository.findById(stageRoleId).map(StageRoleMapper.INSTANCE::mapToStageRoleDTO);
    }

    @Override
    public List<StageRoleDTO> getAllStageRoles() {
        return StageRoleMapper.INSTANCE.mapAllToStageRoleDTOList(stageRoleRepository.findAll());
    }
}
