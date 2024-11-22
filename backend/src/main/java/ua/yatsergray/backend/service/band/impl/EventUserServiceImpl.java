package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventUserDTO;
import ua.yatsergray.backend.domain.entity.band.Event;
import ua.yatsergray.backend.domain.entity.band.EventUser;
import ua.yatsergray.backend.domain.entity.band.ParticipationStatus;
import ua.yatsergray.backend.domain.entity.band.StageRole;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.domain.request.band.EventUserCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventUserUpdateRequest;
import ua.yatsergray.backend.exception.band.*;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.mapper.band.EventUserMapper;
import ua.yatsergray.backend.repository.band.*;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.band.EventUserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class EventUserServiceImpl implements EventUserService {
    private final EventUserMapper eventUserMapper;
    private final EventUserRepository eventUserRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final StageRoleRepository stageRoleRepository;
    private final ParticipationStatusRepository participationStatusRepository;
    private final BandUserStageRoleRepository bandUserStageRoleRepository;
    private final BandUserAccessRoleRepository bandUserAccessRoleRepository;

    @Autowired
    public EventUserServiceImpl(EventUserMapper eventUserMapper, EventUserRepository eventUserRepository, UserRepository userRepository, EventRepository eventRepository, StageRoleRepository stageRoleRepository, ParticipationStatusRepository participationStatusRepository, BandUserStageRoleRepository bandUserStageRoleRepository, BandUserAccessRoleRepository bandUserAccessRoleRepository) {
        this.eventUserMapper = eventUserMapper;
        this.eventUserRepository = eventUserRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.stageRoleRepository = stageRoleRepository;
        this.participationStatusRepository = participationStatusRepository;
        this.bandUserStageRoleRepository = bandUserStageRoleRepository;
        this.bandUserAccessRoleRepository = bandUserAccessRoleRepository;
    }

    @Override
    public EventUserDTO addEventUser(EventUserCreateRequest eventUserCreateRequest) throws NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserAlreadyExistsException, EventUserConflictException {
        User user = userRepository.findById(eventUserCreateRequest.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", eventUserCreateRequest.getUserId())));
        Event event = eventRepository.findById(eventUserCreateRequest.getEventId())
                .orElseThrow(() -> new NoSuchEventException(String.format("Event with id=\"%s\" does not exist", eventUserCreateRequest.getEventId())));
        StageRole stageRole = stageRoleRepository.findById(eventUserCreateRequest.getStageRoleId())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", eventUserCreateRequest.getStageRoleId())));
        ParticipationStatus participationStatus = participationStatusRepository.findById(eventUserCreateRequest.getParticipationStatusId())
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status does not exist with id=\"%s\"", eventUserCreateRequest.getParticipationStatusId())));

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(event.getBand().getId(), eventUserCreateRequest.getUserId())) {
            throw new EventUserConflictException(String.format("User with id=\"%s\" does not belong to the Band with id=\"%s\"", eventUserCreateRequest.getUserId(), event.getBand().getId()));
        }

        if (!bandUserStageRoleRepository.existsByBandIdAndUserIdAndStageRoleId(event.getBand().getId(), eventUserCreateRequest.getUserId(), eventUserCreateRequest.getStageRoleId())) {
            throw new EventUserConflictException(String.format("User with id=\"%s\" does not have the Stage role with id=\"%s\" in the Band with id=\"%s\"", eventUserCreateRequest.getUserId(), eventUserCreateRequest.getStageRoleId(), event.getBand().getId()));
        }

        if (eventUserRepository.existsByEventIdAndUserIdAndStageRoleId(eventUserCreateRequest.getEventId(), eventUserCreateRequest.getUserId(), eventUserCreateRequest.getStageRoleId())) {
            throw new EventUserAlreadyExistsException(String.format("Event user with userId=\"%s\", eventId=\"%s\" and stageRole=\"%s\" already exists", eventUserCreateRequest.getUserId(), eventUserCreateRequest.getEventId(), eventUserCreateRequest.getStageRoleId()));
        }

        EventUser eventUser = EventUser.builder()
                .user(user)
                .event(event)
                .stageRole(stageRole)
                .participationStatus(participationStatus)
                .build();

        return eventUserMapper.mapToEventUserDTO(eventUserRepository.save(eventUser));
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
    public EventUserDTO modifyEventUserById(UUID eventUserId, EventUserUpdateRequest eventUserUpdateRequest) throws NoSuchEventUserException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserAlreadyExistsException, EventUserConflictException {
        EventUser eventUser = eventUserRepository.findById(eventUserId)
                .orElseThrow(() -> new NoSuchEventUserException(String.format("Event user with id=\"%s\" does not exist", eventUserId)));
        StageRole stageRole = stageRoleRepository.findById(eventUserUpdateRequest.getStageRoleId())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", eventUserUpdateRequest.getStageRoleId())));
        ParticipationStatus participationStatus = participationStatusRepository.findById(eventUserUpdateRequest.getParticipationStatusId())
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status does not exist with id=\"%s\"", eventUserUpdateRequest.getParticipationStatusId())));

        if (!bandUserStageRoleRepository.existsByBandIdAndUserIdAndStageRoleId(eventUser.getEvent().getBand().getId(), eventUser.getUser().getId(), eventUserUpdateRequest.getStageRoleId())) {
            throw new EventUserConflictException(String.format("User with id=\"%s\" does not have the Stage role with id=\"%s\" in the Band with id=\"%s\"", eventUser.getUser().getId(), eventUserUpdateRequest.getStageRoleId(), eventUser.getEvent().getBand().getId()));
        }

        if (!eventUserUpdateRequest.getStageRoleId().equals(eventUser.getStageRole().getId()) && eventUserRepository.existsByEventIdAndUserIdAndStageRoleId(eventUser.getEvent().getBand().getId(), eventUser.getUser().getId(), eventUserUpdateRequest.getStageRoleId())) {
            throw new EventUserAlreadyExistsException(String.format("Event user with eventId=\"%s\", userId=\"%s\" and stageRole=\"%s\" already exists", eventUser.getEvent().getBand().getId(), eventUser.getUser().getId(), eventUserUpdateRequest.getStageRoleId()));
        }

        eventUser.setStageRole(stageRole);
        eventUser.setParticipationStatus(participationStatus);

        return eventUserMapper.mapToEventUserDTO(eventUserRepository.save(eventUser));
    }

    @Override
    public void removeEventUserById(UUID eventUserId) throws NoSuchEventUserException {
        if (!eventUserRepository.existsById(eventUserId)) {
            throw new NoSuchEventUserException(String.format("Event user with id=\"%s\" does not exist", eventUserId));
        }

        eventUserRepository.deleteById(eventUserId);
    }
}
