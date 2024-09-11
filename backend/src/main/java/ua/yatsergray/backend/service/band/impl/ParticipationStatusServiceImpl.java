package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ParticipationStatusDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ParticipationStatusEditableDTO;
import ua.yatsergray.backend.domain.entity.band.ParticipationStatus;
import ua.yatsergray.backend.exception.band.NoSuchParticipationStatusException;
import ua.yatsergray.backend.mapper.band.ParticipationStatusMapper;
import ua.yatsergray.backend.repository.band.ParticipationStatusRepository;
import ua.yatsergray.backend.service.band.ParticipationStatusService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipationStatusServiceImpl implements ParticipationStatusService {
    private final ParticipationStatusMapper participationStatusMapper;
    private final ParticipationStatusRepository participationStatusRepository;

    @Autowired
    public ParticipationStatusServiceImpl(ParticipationStatusMapper participationStatusMapper, ParticipationStatusRepository participationStatusRepository) {
        this.participationStatusMapper = participationStatusMapper;
        this.participationStatusRepository = participationStatusRepository;
    }

    @Override
    public ParticipationStatusDTO addParticipationStatus(ParticipationStatusEditableDTO participationStatusEditableDTO) {
        ParticipationStatus participationStatus = ParticipationStatus.builder()
                .name(participationStatusEditableDTO.getName())
                .type(participationStatusEditableDTO.getType())
                .build();

        return participationStatusMapper.mapToParticipationStatusDTO(participationStatusRepository.save(participationStatus));
    }

    @Override
    public Optional<ParticipationStatusDTO> getParticipationStatusById(UUID participationStatusId) {
        return participationStatusRepository.findById(participationStatusId).map(participationStatusMapper::mapToParticipationStatusDTO);
    }

    @Override
    public List<ParticipationStatusDTO> getAllParticipationStatuses() {
        return participationStatusMapper.mapAllToParticipationStatusDTOList(participationStatusRepository.findAll());
    }

    @Override
    public ParticipationStatusDTO modifyParticipationStatusById(UUID participationStatusId, ParticipationStatusEditableDTO participationStatusEditableDTO) throws NoSuchParticipationStatusException {
        return participationStatusRepository.findById(participationStatusId)
                .map(participationStatus -> {
                    participationStatus.setName(participationStatusEditableDTO.getName());
                    participationStatus.setType(participationStatusEditableDTO.getType());

                    return participationStatusMapper.mapToParticipationStatusDTO(participationStatusRepository.save(participationStatus));
                })
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status does not exist with id=%s", participationStatusId)));
    }

    @Override
    public void removeParticipationStatusById(UUID participationStatusId) throws NoSuchParticipationStatusException {
        if (!participationStatusRepository.existsById(participationStatusId)) {
            throw new NoSuchParticipationStatusException(String.format("Participation status does not exist with id=%s", participationStatusId));
        }

        participationStatusRepository.deleteById(participationStatusId);
    }
}
