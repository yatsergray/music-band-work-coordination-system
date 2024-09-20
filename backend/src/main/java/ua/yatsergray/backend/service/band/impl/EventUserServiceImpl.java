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
import ua.yatsergray.backend.exception.band.*;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.mapper.band.EventUserMapper;
import ua.yatsergray.backend.repository.band.*;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.band.EventUserService;

import java.util.List;
import java.util.Objects;
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
    private final BandUserAccessRoleRepository bandUserAccessRoleRepository;
    private final BandUserStageRoleRepository bandUserStageRoleRepository;

    @Autowired
    public EventUserServiceImpl(EventUserMapper eventUserMapper, EventUserRepository eventUserRepository, UserRepository userRepository, EventRepository eventRepository, StageRoleRepository stageRoleRepository, ParticipationStatusRepository participationStatusRepository, BandUserAccessRoleRepository bandUserAccessRoleRepository, BandUserStageRoleRepository bandUserStageRoleRepository) {
        this.eventUserMapper = eventUserMapper;
        this.eventUserRepository = eventUserRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.stageRoleRepository = stageRoleRepository;
        this.participationStatusRepository = participationStatusRepository;
        this.bandUserAccessRoleRepository = bandUserAccessRoleRepository;
        this.bandUserStageRoleRepository = bandUserStageRoleRepository;
    }

    @Override
    public EventUserDTO addEventUser(EventUserEditableDTO eventUserEditableDTO) throws NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserAlreadyExistsException, EventUserConflictException {
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
    public EventUserDTO modifyEventUserById(UUID eventUserId, EventUserEditableDTO eventUserEditableDTO) throws NoSuchEventUserException, NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserAlreadyExistsException, EventUserConflictException {
        EventUser eventUser = eventUserRepository.findById(eventUserId)
                .orElseThrow(() -> new NoSuchEventUserException(String.format("Event user with id=%s does not exist", eventUserId)));

        return eventUserMapper.mapToEventUserDTO(eventUserRepository.save(configureEventUser(eventUser, eventUserEditableDTO)));
    }

    @Override
    public void removeEventUserById(UUID eventUserId) throws NoSuchEventUserException {
        if (!eventUserRepository.existsById(eventUserId)) {
            throw new NoSuchEventUserException(String.format("Event user with id=%s does not exist", eventUserId));
        }

        eventUserRepository.deleteById(eventUserId);
    }

    private EventUser configureEventUser(EventUser eventUser, EventUserEditableDTO eventUserEditableDTO) throws NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserAlreadyExistsException, EventUserConflictException {
        User user = userRepository.findById(eventUserEditableDTO.getEventId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=%s does not exist", eventUserEditableDTO.getEventId())));
        Event event = eventRepository.findById(eventUserEditableDTO.getEventId())
                .orElseThrow(() -> new NoSuchEventException(String.format("Event with id=%s does not exist", eventUserEditableDTO.getEventId())));
        StageRole stageRole = stageRoleRepository.findById(eventUserEditableDTO.getStageRoleId())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=%s does not exist", eventUserEditableDTO.getStageRoleId())));
        ParticipationStatus participationStatus = participationStatusRepository.findById(eventUserEditableDTO.getParticipationStatusId())
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status does not exist with id=%s", eventUserEditableDTO.getParticipationStatusId())));

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(event.getBand().getId(), eventUserEditableDTO.getUserId())) {
            throw new EventUserConflictException(String.format("User with id=%s does not belong to the Band with id=%s", eventUserEditableDTO.getUserId(), eventUserEditableDTO.getEventId()));
        }

        if (!bandUserStageRoleRepository.existsByBandIdAndUserIdAndStageRoleId(event.getBand().getId(), eventUserEditableDTO.getUserId(), eventUserEditableDTO.getStageRoleId())) {
            throw new EventUserConflictException(String.format("User with id=%s does not have the Stage role with id=%s in the Band with id=%s", eventUserEditableDTO.getUserId(), eventUserEditableDTO.getStageRoleId(), event.getBand().getId()));
        }

        if (Objects.isNull(eventUser.getId())) {
            if (eventUserRepository.existsByEventIdAndUserIdAndStageRoleId(eventUserEditableDTO.getEventId(), eventUserEditableDTO.getUserId(), eventUserEditableDTO.getStageRoleId())) {
                throw new EventUserAlreadyExistsException(String.format("EventUser with userId=%s, eventId=%s and stageRole=%s already exists", eventUserEditableDTO.getUserId(), eventUserEditableDTO.getStageRoleId(), eventUserEditableDTO.getEventId()));
            }
        } else {
            if ((!eventUserEditableDTO.getEventId().equals(eventUser.getEvent().getId()) || !eventUserEditableDTO.getUserId().equals(eventUser.getUser().getId()) || !eventUserEditableDTO.getStageRoleId().equals(eventUser.getStageRole().getId())) && eventUserRepository.existsByEventIdAndUserIdAndStageRoleId(eventUserEditableDTO.getEventId(), eventUserEditableDTO.getUserId(), eventUserEditableDTO.getStageRoleId())) {
                throw new EventUserAlreadyExistsException(String.format("EventUser with eventId=%s, userId=%s and stageRole=%s already exists", eventUserEditableDTO.getStageRoleId(), eventUserEditableDTO.getUserId(), eventUserEditableDTO.getEventId()));
            }
        }

        eventUser.setUser(user);
        eventUser.setEvent(event);
        eventUser.setStageRole(stageRole);
        eventUser.setParticipationStatus(participationStatus);

        return eventUser;
    }
}
