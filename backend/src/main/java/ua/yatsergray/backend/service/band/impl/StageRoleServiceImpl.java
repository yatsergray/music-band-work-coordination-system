package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.entity.band.StageRole;
import ua.yatsergray.backend.domain.request.band.StageRoleCreateRequest;
import ua.yatsergray.backend.domain.request.band.StageRoleUpdateRequest;
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
    public StageRoleDTO addStageRole(StageRoleCreateRequest stageRoleCreateRequest) throws StageRoleAlreadyExistsException {
        if (stageRoleRepository.existsByName(stageRoleCreateRequest.getName())) {
            throw new StageRoleAlreadyExistsException(String.format("Stage role with name=\"%s\" already exists", stageRoleCreateRequest.getName()));
        }

        if (stageRoleRepository.existsByType(stageRoleCreateRequest.getType())) {
            throw new StageRoleAlreadyExistsException(String.format("Stage role with type=\"%s\" already exists", stageRoleCreateRequest.getType()));
        }

        StageRole stageRole = StageRole.builder()
                .name(stageRoleCreateRequest.getName())
                .type(stageRoleCreateRequest.getType())
                .build();

        return StageRoleMapper.INSTANCE.mapToStageRoleDTO(stageRoleRepository.save(stageRole));
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
    public StageRoleDTO modifyStageRoleById(UUID stageRoleId, StageRoleUpdateRequest stageRoleUpdateRequest) throws NoSuchStageRoleException, StageRoleAlreadyExistsException {
        StageRole stageRole = stageRoleRepository.findById(stageRoleId)
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", stageRoleId)));

        if (!stageRoleUpdateRequest.getName().equals(stageRole.getName()) && stageRoleRepository.existsByName(stageRoleUpdateRequest.getName())) {
            throw new StageRoleAlreadyExistsException(String.format("Stage role with name=\"%s\" already exists", stageRoleUpdateRequest.getName()));
        }

        stageRole.setName(stageRoleUpdateRequest.getName());

        return StageRoleMapper.INSTANCE.mapToStageRoleDTO(stageRoleRepository.save(stageRole));
    }

    @Override
    public void removeStageRoleById(UUID stageRoleId) throws NoSuchStageRoleException, ChildEntityExistsException {
        if (!stageRoleRepository.existsById(stageRoleId)) {
            throw new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", stageRoleId));
        }

        checkIfStageRoleHasChildEntity(stageRoleId);

        stageRoleRepository.deleteById(stageRoleId);
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
