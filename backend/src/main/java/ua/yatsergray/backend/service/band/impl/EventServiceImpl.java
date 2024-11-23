package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Event;
import ua.yatsergray.backend.domain.entity.band.EventCategory;
import ua.yatsergray.backend.domain.request.band.EventCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventUpdateRequest;
import ua.yatsergray.backend.exception.band.EventConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchEventCategoryException;
import ua.yatsergray.backend.exception.band.NoSuchEventException;
import ua.yatsergray.backend.mapper.band.EventMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.band.EventCategoryRepository;
import ua.yatsergray.backend.repository.band.EventRepository;
import ua.yatsergray.backend.service.band.EventService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final BandRepository bandRepository;
    private final EventCategoryRepository eventCategoryRepository;

    @Autowired
    public EventServiceImpl(EventMapper eventMapper, EventRepository eventRepository, BandRepository bandRepository, EventCategoryRepository eventCategoryRepository) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.bandRepository = bandRepository;
        this.eventCategoryRepository = eventCategoryRepository;
    }

    @Override
    public EventDTO addEvent(EventCreateRequest eventCreateRequest) throws NoSuchBandException, NoSuchEventCategoryException, EventConflictException {
        if (eventCreateRequest.getStartTime().equals(eventCreateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event startTime=\"%s\" and endTime=\"%s\" cannot be equal", eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime()));
        }

        if (eventCreateRequest.getStartTime().isAfter(eventCreateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event startTime=\"%s\" must be before endTime=\"%s\"", eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime()));
        }

        Band band = bandRepository.findById(eventCreateRequest.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", eventCreateRequest.getBandId())));
        EventCategory eventCategory = eventCategoryRepository.findById(eventCreateRequest.getEventCategoryId())
                .orElseThrow(() -> new NoSuchEventCategoryException(String.format("Event category does not exist with id=\"%s\"", eventCreateRequest.getEventCategoryId())));

        if (eventRepository.existsOverlappingEvent(eventCreateRequest.getBandId(), eventCreateRequest.getDate(), eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event with startTime=\"%s\" and endTime=\"%s\" overlaps other event", eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime()));
        }

        Event event = Event.builder()
                .date(eventCreateRequest.getDate())
                .startTime(eventCreateRequest.getStartTime())
                .endTime(eventCreateRequest.getEndTime())
                .band(band)
                .eventCategory(eventCategory)
                .build();

        return eventMapper.mapToEventDTO(eventRepository.save(event));
    }

    @Override
    public Optional<EventDTO> getEventById(UUID eventId) {
        return eventRepository.findById(eventId).map(eventMapper::mapToEventDTO);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventMapper.mapAllToEventDTOList(eventRepository.findAll());
    }

    @Override
    public EventDTO modifyEventById(UUID eventId, EventUpdateRequest eventUpdateRequest) throws NoSuchEventException, NoSuchEventCategoryException, EventConflictException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchEventException(String.format("Event with id=\"%s\" does not exist", eventId)));

        if (eventUpdateRequest.getStartTime().equals(eventUpdateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event startTime=\"%s\" and endTime=\"%s\" cannot be equal", eventUpdateRequest.getStartTime(), eventUpdateRequest.getEndTime()));
        }

        if (eventUpdateRequest.getStartTime().isAfter(eventUpdateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event startTime=\"%s\" must be before endTime=\"%s\"", eventUpdateRequest.getStartTime(), eventUpdateRequest.getEndTime()));
        }

        EventCategory eventCategory = eventCategoryRepository.findById(eventUpdateRequest.getEventCategoryId())
                .orElseThrow(() -> new NoSuchEventCategoryException(String.format("Event category does not exist with id=\"%s\"", eventUpdateRequest.getEventCategoryId())));

        if ((!eventUpdateRequest.getDate().equals(event.getDate()) || !eventUpdateRequest.getStartTime().equals(event.getStartTime()) || !eventUpdateRequest.getEndTime().equals(event.getEndTime())) && eventRepository.existsOverlappingEvent(event.getBand().getId(), eventUpdateRequest.getDate(), eventUpdateRequest.getStartTime(), eventUpdateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event with startTime=\"%s\" and endTime=\"%s\" overlaps other event", eventUpdateRequest.getStartTime(), eventUpdateRequest.getEndTime()));
        }

        event.setDate(eventUpdateRequest.getDate());
        event.setStartTime(eventUpdateRequest.getStartTime());
        event.setEndTime(eventUpdateRequest.getEndTime());
        event.setEventCategory(eventCategory);

        return eventMapper.mapToEventDTO(eventRepository.save(event));
    }

    @Override
    public void removeEventById(UUID eventId) throws NoSuchEventException {
        if (!eventRepository.existsById(eventId)) {
            throw new NoSuchEventException(String.format("Event with id=\"%s\" does not exist", eventId));
        }

        eventRepository.deleteById(eventId);
    }
}
