package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventUserDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventUserEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Event;
import ua.yatsergray.backend.domain.entity.band.EventUser;
import ua.yatsergray.backend.domain.entity.band.ParticipationStatus;
import ua.yatsergray.backend.domain.entity.band.StageRole;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.exception.band.NoSuchEventException;
import ua.yatsergray.backend.exception.band.NoSuchEventUserException;
import ua.yatsergray.backend.exception.band.NoSuchParticipationStatusException;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.mapper.band.EventUserMapper;
import ua.yatsergray.backend.repository.band.EventRepository;
import ua.yatsergray.backend.repository.band.EventUserRepository;
import ua.yatsergray.backend.repository.band.ParticipationStatusRepository;
import ua.yatsergray.backend.repository.band.StageRoleRepository;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.band.EventUserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventUserServiceImpl implements EventUserService {
    private final EventUserMapper eventUserMapper;
    private final EventUserRepository eventUserRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final StageRoleRepository stageRoleRepository;
    private final ParticipationStatusRepository participationStatusRepository;

    @Autowired
    public EventUserServiceImpl(EventUserMapper eventUserMapper, EventUserRepository eventUserRepository, UserRepository userRepository, EventRepository eventRepository, StageRoleRepository stageRoleRepository, ParticipationStatusRepository participationStatusRepository) {
        this.eventUserMapper = eventUserMapper;
        this.eventUserRepository = eventUserRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.stageRoleRepository = stageRoleRepository;
        this.participationStatusRepository = participationStatusRepository;
    }

    @Override
    public EventUserDTO addEventUser(EventUserEditableDTO eventUserEditableDTO) throws NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException {
        return eventUserMapper.mapToEventUserDTO(eventUserRepository.save(configureEventUser(new EventUser(), eventUserEditableDTO)));
    }

    @Override
    public Optional<EventUserDTO> getEventUserById(UUID eventUserId) {
        return eventUserRepository.findById(eventUserId).map(eventUserMapper::mapToEventUserDTO);
    }

    @Override
    public List<EventUserDTO> getAllEventUsers() {
        return eventUserMapper.mapAllToEventUserDTOList(eventUserRepository.findAll());
    }

    @Override
    public EventUserDTO modifyEventUserById(UUID eventUserId, EventUserEditableDTO eventUserEditableDTO) throws NoSuchEventUserException, NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException {
        EventUser eventUser = eventUserRepository.findById(eventUserId)
                .orElseThrow(() -> new NoSuchEventUserException(String.format("Event user does not exist with id=%s", eventUserId)));

        return eventUserMapper.mapToEventUserDTO(eventUserRepository.save(configureEventUser(eventUser, eventUserEditableDTO)));
    }

    @Override
    public void removeEventUserById(UUID eventUserId) throws NoSuchEventUserException {
        if (!eventUserRepository.existsById(eventUserId)) {
            throw new NoSuchEventUserException(String.format("Event user does not exist with id=%s", eventUserId));
        }

        eventUserRepository.deleteById(eventUserId);
    }

    private EventUser configureEventUser(EventUser eventUser, EventUserEditableDTO eventUserEditableDTO) throws NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException {
        User user = userRepository.findById(eventUserEditableDTO.getEventUUID())
                .orElseThrow(() -> new NoSuchUserException(String.format("User does not exist with id=%s", eventUserEditableDTO.getEventUUID())));
        Event event = eventRepository.findById(eventUserEditableDTO.getEventUUID())
                .orElseThrow(() -> new NoSuchEventException(String.format("Event does not exist with id=%s", eventUserEditableDTO.getEventUUID())));
        StageRole stageRole = stageRoleRepository.findById(eventUserEditableDTO.getStageRoleUUID())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role does not exist with id=%s", eventUserEditableDTO.getStageRoleUUID())));
        ParticipationStatus participationStatus = participationStatusRepository.findById(eventUserEditableDTO.getParticipationStatusUUID())
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status does not exist with id=%s", eventUserEditableDTO.getParticipationStatusUUID())));

        eventUser.setUser(user);
        eventUser.setEvent(event);
        eventUser.setStageRole(stageRole);
        eventUser.setParticipationStatus(participationStatus);

        return eventUser;
    }
}
