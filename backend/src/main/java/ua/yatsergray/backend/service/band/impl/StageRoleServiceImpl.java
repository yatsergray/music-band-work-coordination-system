package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.StageRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.StageRole;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.mapper.band.StageRoleMapper;
import ua.yatsergray.backend.repository.band.StageRoleRepository;
import ua.yatsergray.backend.service.band.StageRoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StageRoleServiceImpl implements StageRoleService {
    private final StageRoleMapper stageRoleMapper;
    private final StageRoleRepository stageRoleRepository;

    @Autowired
    public StageRoleServiceImpl(StageRoleMapper stageRoleMapper, StageRoleRepository stageRoleRepository) {
        this.stageRoleMapper = stageRoleMapper;
        this.stageRoleRepository = stageRoleRepository;
    }

    @Override
    public StageRoleDTO addStageRole(StageRoleEditableDTO stageRoleEditableDTO) {
        StageRole stageRole = StageRole.builder()
                .name(stageRoleEditableDTO.getName())
                .type(stageRoleEditableDTO.getType())
                .build();

        return stageRoleMapper.mapToStageRoleDTO(stageRoleRepository.save(stageRole));
    }

    @Override
    public Optional<StageRoleDTO> getStageRoleById(UUID id) {
        return stageRoleRepository.findById(id).map(stageRoleMapper::mapToStageRoleDTO);
    }

    @Override
    public List<StageRoleDTO> getAllStageRoles() {
        return stageRoleMapper.mapAllToStageRoleDTOList(stageRoleRepository.findAll());
    }

    @Override
    public StageRoleDTO modifyStageRoleById(UUID id, StageRoleEditableDTO stageRoleEditableDTO) throws NoSuchStageRoleException {
        return stageRoleRepository.findById(id)
                .map(stageRole -> {
                    stageRole.setName(stageRoleEditableDTO.getName());
                    stageRole.setType(stageRoleEditableDTO.getType());

                    return stageRoleMapper.mapToStageRoleDTO(stageRoleRepository.save(stageRole));
                })
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role does not exist with id=%s", id)));
    }

    @Override
    public void removeStageRoleById(UUID id) throws NoSuchStageRoleException {
        if (!stageRoleRepository.existsById(id)) {
            throw new NoSuchStageRoleException(String.format("Stage role does not exist with id=%s", id));
        }

        stageRoleRepository.deleteById(id);
    }
}
