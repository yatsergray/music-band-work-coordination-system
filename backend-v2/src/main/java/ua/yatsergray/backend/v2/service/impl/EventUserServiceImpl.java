package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.EventUserDTO;
import ua.yatsergray.backend.v2.domain.entity.*;
import ua.yatsergray.backend.v2.domain.request.EventUserCreateRequest;
import ua.yatsergray.backend.v2.domain.request.EventUserUpdateRequest;
import ua.yatsergray.backend.v2.exception.*;
import ua.yatsergray.backend.v2.mapper.EventUserMapper;
import ua.yatsergray.backend.v2.repository.*;
import ua.yatsergray.backend.v2.service.EventUserService;

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
    private final MusicBandUserStageRoleRepository musicBandUserStageRoleRepository;
    private final MusicBandUserAccessRoleRepository musicBandUserAccessRoleRepository;

    @Autowired
    public EventUserServiceImpl(EventUserMapper eventUserMapper, EventUserRepository eventUserRepository, UserRepository userRepository, EventRepository eventRepository, StageRoleRepository stageRoleRepository, ParticipationStatusRepository participationStatusRepository, MusicBandUserStageRoleRepository musicBandUserStageRoleRepository, MusicBandUserAccessRoleRepository musicBandUserAccessRoleRepository) {
        this.eventUserMapper = eventUserMapper;
        this.eventUserRepository = eventUserRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.stageRoleRepository = stageRoleRepository;
        this.participationStatusRepository = participationStatusRepository;
        this.musicBandUserStageRoleRepository = musicBandUserStageRoleRepository;
        this.musicBandUserAccessRoleRepository = musicBandUserAccessRoleRepository;
    }

    @Override
    public EventUserDTO addEventUser(EventUserCreateRequest eventUserCreateRequest) throws NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserConflictException, EventUserAlreadyExistsException {
        User user = userRepository.findById(eventUserCreateRequest.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", eventUserCreateRequest.getUserId())));
        Event event = eventRepository.findById(eventUserCreateRequest.getEventId())
                .orElseThrow(() -> new NoSuchEventException(String.format("Event with id=\"%s\" does not exist", eventUserCreateRequest.getEventId())));
        StageRole stageRole = stageRoleRepository.findById(eventUserCreateRequest.getStageRoleId())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", eventUserCreateRequest.getStageRoleId())));
        ParticipationStatus participationStatus = participationStatusRepository.findById(eventUserCreateRequest.getParticipationStatusId())
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status does not exist with id=\"%s\"", eventUserCreateRequest.getParticipationStatusId())));

        if (!musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(event.getMusicBand().getId(), eventUserCreateRequest.getUserId())) {
            throw new EventUserConflictException(String.format("User with id=\"%s\" does not belong to the Music band with id=\"%s\"", eventUserCreateRequest.getUserId(), event.getMusicBand().getId()));
        }

        if (!musicBandUserStageRoleRepository.existsByMusicBandIdAndUserIdAndStageRoleId(event.getMusicBand().getId(), eventUserCreateRequest.getUserId(), eventUserCreateRequest.getStageRoleId())) {
            throw new EventUserConflictException(String.format("User with id=\"%s\" does not have the Stage role with id=\"%s\" in the Music band with id=\"%s\"", eventUserCreateRequest.getUserId(), eventUserCreateRequest.getStageRoleId(), event.getMusicBand().getId()));
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
    public EventUserDTO modifyEventUserById(UUID eventUserId, EventUserUpdateRequest eventUserUpdateRequest) throws NoSuchEventUserException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserConflictException, EventUserAlreadyExistsException {
        EventUser eventUser = eventUserRepository.findById(eventUserId)
                .orElseThrow(() -> new NoSuchEventUserException(String.format("Event user with id=\"%s\" does not exist", eventUserId)));
        StageRole stageRole = stageRoleRepository.findById(eventUserUpdateRequest.getStageRoleId())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", eventUserUpdateRequest.getStageRoleId())));
        ParticipationStatus participationStatus = participationStatusRepository.findById(eventUserUpdateRequest.getParticipationStatusId())
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status does not exist with id=\"%s\"", eventUserUpdateRequest.getParticipationStatusId())));

        if (!musicBandUserStageRoleRepository.existsByMusicBandIdAndUserIdAndStageRoleId(eventUser.getEvent().getMusicBand().getId(), eventUser.getUser().getId(), eventUserUpdateRequest.getStageRoleId())) {
            throw new EventUserConflictException(String.format("User with id=\"%s\" does not have the Stage role with id=\"%s\" in the Music band with id=\"%s\"", eventUser.getUser().getId(), eventUserUpdateRequest.getStageRoleId(), eventUser.getEvent().getMusicBand().getId()));
        }

        if (!eventUserUpdateRequest.getStageRoleId().equals(eventUser.getStageRole().getId()) && eventUserRepository.existsByEventIdAndUserIdAndStageRoleId(eventUser.getEvent().getMusicBand().getId(), eventUser.getUser().getId(), eventUserUpdateRequest.getStageRoleId())) {
            throw new EventUserAlreadyExistsException(String.format("Event user with eventId=\"%s\", userId=\"%s\" and stageRole=\"%s\" already exists", eventUser.getEvent().getMusicBand().getId(), eventUser.getUser().getId(), eventUserUpdateRequest.getStageRoleId()));
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
