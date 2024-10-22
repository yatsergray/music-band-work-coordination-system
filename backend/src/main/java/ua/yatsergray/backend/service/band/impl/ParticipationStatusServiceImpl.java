package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ParticipationStatusDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ParticipationStatusEditableDTO;
import ua.yatsergray.backend.domain.entity.band.ParticipationStatus;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchParticipationStatusException;
import ua.yatsergray.backend.exception.band.ParticipationStatusAlreadyExistsException;
import ua.yatsergray.backend.mapper.band.ParticipationStatusMapper;
import ua.yatsergray.backend.repository.band.EventUserRepository;
import ua.yatsergray.backend.repository.band.InvitationRepository;
import ua.yatsergray.backend.repository.band.ParticipationStatusRepository;
import ua.yatsergray.backend.service.band.ParticipationStatusService;

import java.util.List;
import java.util.Objects;
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
    public ParticipationStatusDTO addParticipationStatus(ParticipationStatusEditableDTO participationStatusEditableDTO) throws ParticipationStatusAlreadyExistsException {
        return participationStatusMapper.mapToParticipationStatusDTO(participationStatusRepository.save(configureParticipationStatus(new ParticipationStatus(), participationStatusEditableDTO)));
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
    public ParticipationStatusDTO modifyParticipationStatusById(UUID participationStatusId, ParticipationStatusEditableDTO participationStatusEditableDTO) throws NoSuchParticipationStatusException, ParticipationStatusAlreadyExistsException {
        ParticipationStatus participationStatus = participationStatusRepository.findById(participationStatusId)
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status with id=\"%s\" does not exist", participationStatusId)));

        return participationStatusMapper.mapToParticipationStatusDTO(participationStatusRepository.save(configureParticipationStatus(participationStatus, participationStatusEditableDTO)));
    }

    @Override
    public void removeParticipationStatusById(UUID participationStatusId) throws NoSuchParticipationStatusException, ChildEntityExistsException {
        if (!participationStatusRepository.existsById(participationStatusId)) {
            throw new NoSuchParticipationStatusException(String.format("Participation status with id=\"%s\" does not exist", participationStatusId));
        }

        checkIfParticipationStatusHasChildEntity(participationStatusId);

        participationStatusRepository.deleteById(participationStatusId);
    }

    private ParticipationStatus configureParticipationStatus(ParticipationStatus participationStatus, ParticipationStatusEditableDTO participationStatusEditableDTO) throws ParticipationStatusAlreadyExistsException {
        if (Objects.isNull(participationStatus.getId())) {
            if (participationStatusRepository.existsByName(participationStatusEditableDTO.getName())) {
                throw new ParticipationStatusAlreadyExistsException(String.format("Participation status with name=\"%s\" already exists", participationStatusEditableDTO.getName()));
            }

            if (participationStatusRepository.existsByType(participationStatusEditableDTO.getType())) {
                throw new ParticipationStatusAlreadyExistsException(String.format("Participation status with type=\"%s\" already exists", participationStatusEditableDTO.getType()));
            }
        } else {
            if (!participationStatusEditableDTO.getName().equals(participationStatus.getName()) && participationStatusRepository.existsByName(participationStatusEditableDTO.getName())) {
                throw new ParticipationStatusAlreadyExistsException(String.format("Participation status with name=\"%s\" already exists", participationStatusEditableDTO.getName()));
            }

            if (!participationStatusEditableDTO.getType().equals(participationStatus.getType()) && participationStatusRepository.existsByType(participationStatusEditableDTO.getType())) {
                throw new ParticipationStatusAlreadyExistsException(String.format("Participation status with type=\"%s\" already exists", participationStatusEditableDTO.getType()));
            }
        }

        participationStatus.setName(participationStatusEditableDTO.getName());
        participationStatus.setType(participationStatusEditableDTO.getType());

        return participationStatus;
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
