package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.StageRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.StageRole;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.band.StageRoleAlreadyExistsException;
import ua.yatsergray.backend.mapper.band.StageRoleMapper;
import ua.yatsergray.backend.repository.band.BandUserStageRoleRepository;
import ua.yatsergray.backend.repository.band.EventUserRepository;
import ua.yatsergray.backend.repository.band.StageRoleRepository;
import ua.yatsergray.backend.repository.song.SongInstrumentalPartRepository;
import ua.yatsergray.backend.service.band.StageRoleService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class StageRoleServiceImpl implements StageRoleService {
    private final StageRoleRepository stageRoleRepository;
    private final EventUserRepository eventUserRepository;
    private final BandUserStageRoleRepository bandUserStageRoleRepository;
    private final SongInstrumentalPartRepository songInstrumentalPartRepository;

    @Autowired
    public StageRoleServiceImpl(StageRoleRepository stageRoleRepository, EventUserRepository eventUserRepository, BandUserStageRoleRepository bandUserStageRoleRepository, SongInstrumentalPartRepository songInstrumentalPartRepository) {
        this.stageRoleRepository = stageRoleRepository;
        this.eventUserRepository = eventUserRepository;
        this.bandUserStageRoleRepository = bandUserStageRoleRepository;
        this.songInstrumentalPartRepository = songInstrumentalPartRepository;
    }

    @Override
    public StageRoleDTO addStageRole(StageRoleEditableDTO stageRoleEditableDTO) throws StageRoleAlreadyExistsException {
        return StageRoleMapper.INSTANCE.mapToStageRoleDTO(stageRoleRepository.save(configureStageRole(new StageRole(), stageRoleEditableDTO)));
    }

    @Override
    public Optional<StageRoleDTO> getStageRoleById(UUID stageRoleId) {
        return stageRoleRepository.findById(stageRoleId).map(StageRoleMapper.INSTANCE::mapToStageRoleDTO);
    }

    @Override
    public List<StageRoleDTO> getAllStageRoles() {
        return StageRoleMapper.INSTANCE.mapAllToStageRoleDTOList(stageRoleRepository.findAll());
    }

    @Override
    public StageRoleDTO modifyStageRoleById(UUID stageRoleId, StageRoleEditableDTO stageRoleEditableDTO) throws NoSuchStageRoleException, StageRoleAlreadyExistsException {
        StageRole stageRole = stageRoleRepository.findById(stageRoleId)
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", stageRoleId)));

        return StageRoleMapper.INSTANCE.mapToStageRoleDTO(stageRoleRepository.save(configureStageRole(stageRole, stageRoleEditableDTO)));
    }

    @Override
    public void removeStageRoleById(UUID stageRoleId) throws NoSuchStageRoleException, ChildEntityExistsException {
        if (!stageRoleRepository.existsById(stageRoleId)) {
            throw new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", stageRoleId));
        }

        checkIfStageRoleHasChildEntity(stageRoleId);

        stageRoleRepository.deleteById(stageRoleId);
    }

    private StageRole configureStageRole(StageRole stageRole, StageRoleEditableDTO stageRoleEditableDTO) throws StageRoleAlreadyExistsException {
        if (Objects.isNull(stageRole.getId())) {
            if (stageRoleRepository.existsByName(stageRoleEditableDTO.getName())) {
                throw new StageRoleAlreadyExistsException(String.format("Stage role with name=\"%s\" already exists", stageRoleEditableDTO.getName()));
            }

            if (stageRoleRepository.existsByType(stageRoleEditableDTO.getType())) {
                throw new StageRoleAlreadyExistsException(String.format("Stage role with type=\"%s\" already exists", stageRoleEditableDTO.getType()));
            }
        } else {
            if (!stageRoleEditableDTO.getName().equals(stageRole.getName()) && stageRoleRepository.existsByName(stageRoleEditableDTO.getName())) {
                throw new StageRoleAlreadyExistsException(String.format("Stage role with name=\"%s\" already exists", stageRoleEditableDTO.getName()));
            }

            if (!stageRoleEditableDTO.getType().equals(stageRole.getType()) && stageRoleRepository.existsByType(stageRoleEditableDTO.getType())) {
                throw new StageRoleAlreadyExistsException(String.format("Stage role with type=\"%s\" already exists", stageRoleEditableDTO.getType()));
            }
        }

        stageRole.setName(stageRoleEditableDTO.getName());
        stageRole.setType(stageRoleEditableDTO.getType());

        return stageRoleRepository.save(stageRole);
    }

    private void checkIfStageRoleHasChildEntity(UUID stageRoleId) throws ChildEntityExistsException {
        long stageRoleChildEntityAmount = eventUserRepository.countByStageRoleId(stageRoleId);

        if (stageRoleChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d Event user(s) depend(s) on the Stage role with id=%s", stageRoleChildEntityAmount, stageRoleId));
        }

        stageRoleChildEntityAmount = bandUserStageRoleRepository.countByStageRoleId(stageRoleId);

        if (stageRoleChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d Band user stage role(s) depend(s) on the Stage role with id=%s", stageRoleChildEntityAmount, stageRoleId));
        }

        stageRoleChildEntityAmount = songInstrumentalPartRepository.countByStageRoleId(stageRoleId);

        if (stageRoleChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d Song instrumental part(s) depend(s) on the Stage role with id=%s", stageRoleChildEntityAmount, stageRoleId));
        }
    }
}
