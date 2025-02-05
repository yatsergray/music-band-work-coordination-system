package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventStatusDTO;
import ua.yatsergray.backend.domain.entity.band.EventStatus;
import ua.yatsergray.backend.domain.request.band.EventStatusCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventStatusUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.EventStatusAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchEventStatusException;
import ua.yatsergray.backend.mapper.band.EventStatusMapper;
import ua.yatsergray.backend.repository.band.EventRepository;
import ua.yatsergray.backend.repository.band.EventStatusRepository;
import ua.yatsergray.backend.service.band.EventStatusService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class EventStatusServiceImpl implements EventStatusService {
    private final EventStatusMapper eventStatusMapper;
    private final EventStatusRepository eventStatusRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventStatusServiceImpl(EventStatusMapper eventStatusMapper, EventStatusRepository eventStatusRepository, EventRepository eventRepository) {
        this.eventStatusMapper = eventStatusMapper;
        this.eventStatusRepository = eventStatusRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public EventStatusDTO addEventStatus(EventStatusCreateRequest eventStatusCreateRequest) throws EventStatusAlreadyExistsException {
        if (eventStatusRepository.existsByName(eventStatusCreateRequest.getName())) {
            throw new EventStatusAlreadyExistsException(String.format("Event status with name=\"%s\" already exists", eventStatusCreateRequest.getName()));
        }

        if (eventStatusRepository.existsByType(eventStatusCreateRequest.getType())) {
            throw new EventStatusAlreadyExistsException(String.format("Event status with type=\"%s\" already exists", eventStatusCreateRequest.getType()));
        }

        EventStatus eventStatus = EventStatus.builder()
                .name(eventStatusCreateRequest.getName())
                .type(eventStatusCreateRequest.getType())
                .build();

        return eventStatusMapper.mapToEventStatusDTO(eventStatusRepository.save(eventStatus));
    }

    @Override
    public Optional<EventStatusDTO> getEventStatusById(UUID eventStatusId) {
        return eventStatusRepository.findById(eventStatusId).map(eventStatusMapper::mapToEventStatusDTO);
    }

    @Override
    public List<EventStatusDTO> getAllEventStatuses() {
        return eventStatusMapper.mapAllToEventStatusDTOList(eventStatusRepository.findAll());
    }

    @Override
    public EventStatusDTO modifyEventStatusById(UUID eventStatusId, EventStatusUpdateRequest eventStatusUpdateRequest) throws NoSuchEventStatusException, EventStatusAlreadyExistsException {
        EventStatus eventStatus = eventStatusRepository.findById(eventStatusId)
                .orElseThrow(() -> new NoSuchEventStatusException(String.format("Event status with id=\"%s\" does not exist", eventStatusId)));

        if (!eventStatusUpdateRequest.getName().equals(eventStatus.getName()) && eventStatusRepository.existsByName(eventStatusUpdateRequest.getName())) {
            throw new EventStatusAlreadyExistsException(String.format("Event status with name=\"%s\" already exists", eventStatusUpdateRequest.getName()));
        }

        eventStatus.setName(eventStatusUpdateRequest.getName());

        return eventStatusMapper.mapToEventStatusDTO(eventStatusRepository.save(eventStatus));
    }

    @Override
    public void removeEventStatusById(UUID eventStatusId) throws NoSuchEventStatusException, ChildEntityExistsException {
        if (!eventStatusRepository.existsById(eventStatusId)) {
            throw new NoSuchEventStatusException(String.format("Event status with id=\"%s\" does not exist", eventStatusId));
        }

        checkIfEventStatusHasChildEntity(eventStatusId);

        eventStatusRepository.deleteById(eventStatusId);
    }

    private void checkIfEventStatusHasChildEntity(UUID eventStatusId) throws ChildEntityExistsException {
        long eventStatusChildEntityAmount = eventRepository.countByEventStatusId(eventStatusId);

        if (eventStatusChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Event(s) depend(s) on the Event status with id=\"%s\"", eventStatusChildEntityAmount, eventStatusId));
        }
    }
}
