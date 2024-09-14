package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.StageRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.StageRole;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.band.StageRoleAlreadyExistsException;
import ua.yatsergray.backend.mapper.band.StageRoleMapper;
import ua.yatsergray.backend.repository.band.StageRoleRepository;
import ua.yatsergray.backend.service.band.StageRoleService;

import java.util.List;
import java.util.Objects;
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
    public StageRoleDTO addStageRole(StageRoleEditableDTO stageRoleEditableDTO) throws StageRoleAlreadyExistsException {
        return stageRoleMapper.mapToStageRoleDTO(stageRoleRepository.save(configureStageRole(new StageRole(), stageRoleEditableDTO)));
    }

    @Override
    public Optional<StageRoleDTO> getStageRoleById(UUID stageRoleId) {
        return stageRoleRepository.findById(stageRoleId).map(stageRoleMapper::mapToStageRoleDTO);
    }

    @Override
    public List<StageRoleDTO> getAllStageRoles() {
        return stageRoleMapper.mapAllToStageRoleDTOList(stageRoleRepository.findAll());
    }

    @Override
    public StageRoleDTO modifyStageRoleById(UUID stageRoleId, StageRoleEditableDTO stageRoleEditableDTO) throws NoSuchStageRoleException, StageRoleAlreadyExistsException {
        StageRole stageRole = stageRoleRepository.findById(stageRoleId)
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=%s does not exist", stageRoleId)));

        return stageRoleMapper.mapToStageRoleDTO(stageRoleRepository.save(configureStageRole(stageRole, stageRoleEditableDTO)));
    }

    @Override
    public void removeStageRoleById(UUID stageRoleId) throws NoSuchStageRoleException {
        if (!stageRoleRepository.existsById(stageRoleId)) {
            throw new NoSuchStageRoleException(String.format("Stage role with id=%s does not exist", stageRoleId));
        }

        stageRoleRepository.deleteById(stageRoleId);
    }

    private StageRole configureStageRole(StageRole stageRole, StageRoleEditableDTO stageRoleEditableDTO) throws StageRoleAlreadyExistsException {
        if (Objects.isNull(stageRole.getId())) {
            if (stageRoleRepository.existsByName(stageRoleEditableDTO.getName())) {
                throw new StageRoleAlreadyExistsException(String.format("Stage role with name=%s already exists", stageRoleEditableDTO.getName()));
            }

            if (stageRoleRepository.existsByType(stageRoleEditableDTO.getType())) {
                throw new StageRoleAlreadyExistsException(String.format("Stage role with type=%s already exists", stageRoleEditableDTO.getType()));
            }
        } else {
            if (!stageRoleEditableDTO.getName().equals(stageRole.getName()) && stageRoleRepository.existsByName(stageRoleEditableDTO.getName())) {
                throw new StageRoleAlreadyExistsException(String.format("Stage role with name=%s already exists", stageRoleEditableDTO.getName()));
            }

            if (!stageRoleEditableDTO.getType().equals(stageRole.getType()) && stageRoleRepository.existsByType(stageRoleEditableDTO.getType())) {
                throw new StageRoleAlreadyExistsException(String.format("Stage role with type=%s already exists", stageRoleEditableDTO.getType()));
            }
        }

        stageRole.setName(stageRoleEditableDTO.getName());
        stageRole.setType(stageRoleEditableDTO.getType());

        return stageRoleRepository.save(stageRole);
    }
}
