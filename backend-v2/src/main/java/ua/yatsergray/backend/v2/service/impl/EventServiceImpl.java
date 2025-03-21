package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.EventDTO;
import ua.yatsergray.backend.v2.domain.entity.Event;
import ua.yatsergray.backend.v2.domain.entity.EventCategory;
import ua.yatsergray.backend.v2.domain.entity.MusicBand;
import ua.yatsergray.backend.v2.domain.request.EventCreateRequest;
import ua.yatsergray.backend.v2.domain.request.EventUpdateRequest;
import ua.yatsergray.backend.v2.exception.EventConflictException;
import ua.yatsergray.backend.v2.exception.NoSuchEventCategoryException;
import ua.yatsergray.backend.v2.exception.NoSuchEventException;
import ua.yatsergray.backend.v2.exception.NoSuchMusicBandException;
import ua.yatsergray.backend.v2.mapper.EventMapper;
import ua.yatsergray.backend.v2.repository.EventCategoryRepository;
import ua.yatsergray.backend.v2.repository.EventRepository;
import ua.yatsergray.backend.v2.repository.MusicBandRepository;
import ua.yatsergray.backend.v2.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final MusicBandRepository musicBandRepository;
    private final EventCategoryRepository eventCategoryRepository;

    @Autowired
    public EventServiceImpl(EventMapper eventMapper, EventRepository eventRepository, MusicBandRepository musicBandRepository, EventCategoryRepository eventCategoryRepository) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.musicBandRepository = musicBandRepository;
        this.eventCategoryRepository = eventCategoryRepository;
    }

    @Override
    public EventDTO addEvent(EventCreateRequest eventCreateRequest) throws EventConflictException, NoSuchMusicBandException, NoSuchEventCategoryException {
        if (eventCreateRequest.getStartTime().equals(eventCreateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event startTime=\"%s\" and endTime=\"%s\" cannot be equal", eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime()));
        }

        if (eventCreateRequest.getStartTime().isAfter(eventCreateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event startTime=\"%s\" must be before endTime=\"%s\"", eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime()));
        }

        MusicBand musicBand = musicBandRepository.findById(eventCreateRequest.getMusicBandId())
                .orElseThrow(() -> new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", eventCreateRequest.getMusicBandId())));
        EventCategory eventCategory = eventCategoryRepository.findById(eventCreateRequest.getEventCategoryId())
                .orElseThrow(() -> new NoSuchEventCategoryException(String.format("Event category with id=\"%s\" does not exist", eventCreateRequest.getEventCategoryId())));

        if (eventRepository.existsOverlappingEvent(eventCreateRequest.getMusicBandId(), eventCreateRequest.getDate(), eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event with startTime=\"%s\" and endTime=\"%s\" overlaps other event", eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime()));
        }

        Event event = Event.builder()
                .date(eventCreateRequest.getDate())
                .startTime(eventCreateRequest.getStartTime())
                .endTime(eventCreateRequest.getEndTime())
                .createdAt(LocalDateTime.now())
                .musicBand(musicBand)
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
    public EventDTO modifyEventById(UUID eventId, EventUpdateRequest eventUpdateRequest) throws NoSuchEventException, EventConflictException, NoSuchEventCategoryException {
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

        if ((!eventUpdateRequest.getDate().equals(event.getDate()) || !eventUpdateRequest.getStartTime().equals(event.getStartTime()) || !eventUpdateRequest.getEndTime().equals(event.getEndTime())) && eventRepository.existsOverlappingEvent(event.getMusicBand().getId(), eventUpdateRequest.getDate(), eventUpdateRequest.getStartTime(), eventUpdateRequest.getEndTime())) {
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
