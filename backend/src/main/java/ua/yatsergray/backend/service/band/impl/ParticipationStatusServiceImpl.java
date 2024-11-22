package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ParticipationStatusDTO;
import ua.yatsergray.backend.domain.entity.band.ParticipationStatus;
import ua.yatsergray.backend.domain.request.band.ParticipationStatusCreateRequest;
import ua.yatsergray.backend.domain.request.band.ParticipationStatusUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchParticipationStatusException;
import ua.yatsergray.backend.exception.band.ParticipationStatusAlreadyExistsException;
import ua.yatsergray.backend.mapper.band.ParticipationStatusMapper;
import ua.yatsergray.backend.repository.band.EventUserRepository;
import ua.yatsergray.backend.repository.band.InvitationRepository;
import ua.yatsergray.backend.repository.band.ParticipationStatusRepository;
import ua.yatsergray.backend.service.band.ParticipationStatusService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ParticipationStatusServiceImpl implements ParticipationStatusService {
    private final ParticipationStatusMapper participationStatusMapper;
    private final ParticipationStatusRepository participationStatusRepository;
    private final EventUserRepository eventUserRepository;
    private final InvitationRepository invitationRepository;

    @Autowired
    public ParticipationStatusServiceImpl(ParticipationStatusMapper participationStatusMapper, ParticipationStatusRepository participationStatusRepository, EventUserRepository eventUserRepository, InvitationRepository invitationRepository) {
        this.participationStatusMapper = participationStatusMapper;
        this.participationStatusRepository = participationStatusRepository;
        this.eventUserRepository = eventUserRepository;
        this.invitationRepository = invitationRepository;
    }

    @Override
    public ParticipationStatusDTO addParticipationStatus(ParticipationStatusCreateRequest participationStatusCreateRequest) throws ParticipationStatusAlreadyExistsException {
        if (participationStatusRepository.existsByName(participationStatusCreateRequest.getName())) {
            throw new ParticipationStatusAlreadyExistsException(String.format("Participation status with name=\"%s\" already exists", participationStatusCreateRequest.getName()));
        }

        if (participationStatusRepository.existsByType(participationStatusCreateRequest.getType())) {
            throw new ParticipationStatusAlreadyExistsException(String.format("Participation status with type=\"%s\" already exists", participationStatusCreateRequest.getType()));
        }

        ParticipationStatus participationStatus = ParticipationStatus.builder()
                .name(participationStatusCreateRequest.getName())
                .type(participationStatusCreateRequest.getType())
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
    public ParticipationStatusDTO modifyParticipationStatusById(UUID participationStatusId, ParticipationStatusUpdateRequest participationStatusUpdateRequest) throws NoSuchParticipationStatusException, ParticipationStatusAlreadyExistsException {
        ParticipationStatus participationStatus = participationStatusRepository.findById(participationStatusId)
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status with id=\"%s\" does not exist", participationStatusId)));

        if (!participationStatusUpdateRequest.getName().equals(participationStatus.getName()) && participationStatusRepository.existsByName(participationStatusUpdateRequest.getName())) {
            throw new ParticipationStatusAlreadyExistsException(String.format("Participation status with name=\"%s\" already exists", participationStatusUpdateRequest.getName()));
        }

        participationStatus.setName(participationStatusUpdateRequest.getName());

        return participationStatusMapper.mapToParticipationStatusDTO(participationStatusRepository.save(participationStatus));
    }

    @Override
    public void removeParticipationStatusById(UUID participationStatusId) throws NoSuchParticipationStatusException, ChildEntityExistsException {
        if (!participationStatusRepository.existsById(participationStatusId)) {
            throw new NoSuchParticipationStatusException(String.format("Participation status with id=\"%s\" does not exist", participationStatusId));
        }

        checkIfParticipationStatusHasChildEntity(participationStatusId);

        participationStatusRepository.deleteById(participationStatusId);
    }

    private void checkIfParticipationStatusHasChildEntity(UUID participationStatusId) throws ChildEntityExistsException {
        long participationStatusChildEntityAmount = eventUserRepository.countByParticipationStatusId(participationStatusId);

        if (participationStatusChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Event user(s) depend(s) on the Participation status with id=%s", participationStatusChildEntityAmount, participationStatusId));
        }

        participationStatusChildEntityAmount = invitationRepository.countByParticipationStatusId(participationStatusId);

        if (participationStatusChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Invitation(s) depend(s) on the Participation status with id=%s", participationStatusChildEntityAmount, participationStatusId));
        }
    }
}
