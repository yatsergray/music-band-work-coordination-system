package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Event;
import ua.yatsergray.backend.domain.entity.band.EventCategory;
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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
    public EventDTO addEvent(EventEditableDTO eventEditableDTO) throws NoSuchBandException, NoSuchEventCategoryException, EventConflictException {
        return eventMapper.mapToEventDTO(eventRepository.save(configureEvent(new Event(), eventEditableDTO)));
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
    public EventDTO modifyEventById(UUID eventId, EventEditableDTO eventEditableDTO) throws NoSuchEventException, NoSuchBandException, NoSuchEventCategoryException, EventConflictException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchEventException(String.format("Event with id=%s does not exist", eventId)));

        return eventMapper.mapToEventDTO(eventRepository.save(configureEvent(event, eventEditableDTO)));
    }

    @Override
    public void removeEventById(UUID eventId) throws NoSuchEventException {
        if (!eventRepository.existsById(eventId)) {
            throw new NoSuchEventException(String.format("Event with id=%s does not exist", eventId));
        }

        eventRepository.deleteById(eventId);
    }

    private Event configureEvent(Event event, EventEditableDTO eventEditableDTO) throws NoSuchBandException, NoSuchEventCategoryException, EventConflictException {
        if (eventEditableDTO.getStartTime().equals(eventEditableDTO.getEndTime())) {
            throw new EventConflictException(String.format("Event startTime=%s and endTime=%s cannot be equal", eventEditableDTO.getStartTime(), eventEditableDTO.getEndTime()));
        }

        if (eventEditableDTO.getStartTime().isAfter(eventEditableDTO.getEndTime())) {
            throw new EventConflictException(String.format("Event startTime=%s must be before endTime=%s", eventEditableDTO.getStartTime(), eventEditableDTO.getEndTime()));
        }

        Band band = bandRepository.findById(eventEditableDTO.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=%s does not exist", eventEditableDTO.getBandId())));
        EventCategory eventCategory = eventCategoryRepository.findById(eventEditableDTO.getEventCategoryId())
                .orElseThrow(() -> new NoSuchEventCategoryException(String.format("Event category does not exist with id=%s", eventEditableDTO.getEventCategoryId())));

        if (Objects.isNull(event.getId())) {
            if (eventRepository.existsOverlappingEvent(eventEditableDTO.getBandId(), eventEditableDTO.getDate(), eventEditableDTO.getStartTime(), eventEditableDTO.getEndTime())) {
                throw new EventConflictException(String.format("Event with startTime=%s and endTime=%s overlaps other event", eventEditableDTO.getStartTime(), eventEditableDTO.getEndTime()));
            }
        } else {
            if ((!eventEditableDTO.getBandId().equals(event.getBand().getId()) || !eventEditableDTO.getDate().equals(event.getDate()) || !eventEditableDTO.getStartTime().equals(event.getStartTime()) || !eventEditableDTO.getEndTime().equals(event.getEndTime())) && eventRepository.existsOverlappingEvent(eventEditableDTO.getBandId(), eventEditableDTO.getDate(), eventEditableDTO.getStartTime(), eventEditableDTO.getEndTime())) {
                throw new EventConflictException(String.format("Event with startTime=%s and endTime=%s overlaps other event", eventEditableDTO.getStartTime(), eventEditableDTO.getEndTime()));
            }
        }

        event.setDate(eventEditableDTO.getDate());
        event.setStartTime(eventEditableDTO.getStartTime());
        event.setEndTime(eventEditableDTO.getEndTime());
        event.setBand(band);
        event.setEventCategory(eventCategory);

        return event;
    }
}
