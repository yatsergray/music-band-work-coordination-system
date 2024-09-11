package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Event;
import ua.yatsergray.backend.domain.entity.band.EventCategory;
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
    public EventDTO addEvent(EventEditableDTO eventEditableDTO) throws NoSuchBandException, NoSuchEventCategoryException {
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
    public EventDTO modifyEventById(UUID eventId, EventEditableDTO eventEditableDTO) throws NoSuchEventException, NoSuchBandException, NoSuchEventCategoryException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchEventException(String.format("Event does not exist with id=%s", eventId)));

        return eventMapper.mapToEventDTO(eventRepository.save(configureEvent(event, eventEditableDTO)));
    }

    @Override
    public void removeEventById(UUID eventId) throws NoSuchEventException {
        if (!eventRepository.existsById(eventId)) {
            throw new NoSuchEventException(String.format("Event does not exist with id=%s", eventId));
        }

        eventRepository.deleteById(eventId);
    }

    private Event configureEvent(Event event, EventEditableDTO eventEditableDTO) throws NoSuchBandException, NoSuchEventCategoryException {
        Band band = bandRepository.findById(eventEditableDTO.getBandUUID())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band does not exist with id=%s", eventEditableDTO.getBandUUID())));
        EventCategory eventCategory = eventCategoryRepository.findById(eventEditableDTO.getEventCategoryUUID())
                .orElseThrow(() -> new NoSuchEventCategoryException(String.format("Event category does not exist with id=%s", eventEditableDTO.getEventCategoryUUID())));

        event.setDate(eventEditableDTO.getDate());
        event.setStartTime(eventEditableDTO.getStartTime());
        event.setEndTime(eventEditableDTO.getEndTime());
        event.setBand(band);
        event.setEventCategory(eventCategory);

        return event;
    }
}
