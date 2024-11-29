package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Event;
import ua.yatsergray.backend.domain.entity.band.EventCategory;
import ua.yatsergray.backend.domain.entity.band.Room;
import ua.yatsergray.backend.domain.request.band.EventCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventUpdateRequest;
import ua.yatsergray.backend.exception.band.*;
import ua.yatsergray.backend.mapper.band.EventMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.band.EventCategoryRepository;
import ua.yatsergray.backend.repository.band.EventRepository;
import ua.yatsergray.backend.repository.band.RoomRepository;
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
    private final RoomRepository roomRepository;

    @Autowired
    public EventServiceImpl(EventMapper eventMapper, EventRepository eventRepository, BandRepository bandRepository, EventCategoryRepository eventCategoryRepository, RoomRepository roomRepository) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.bandRepository = bandRepository;
        this.eventCategoryRepository = eventCategoryRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public EventDTO addEvent(EventCreateRequest eventCreateRequest) throws NoSuchBandException, NoSuchEventCategoryException, EventConflictException, NoSuchRoomException, EventRoomConflictException {
        if (eventCreateRequest.getStartTime().equals(eventCreateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event startTime=\"%s\" and endTime=\"%s\" cannot be equal", eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime()));
        }

        if (eventCreateRequest.getStartTime().isAfter(eventCreateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event startTime=\"%s\" must be before endTime=\"%s\"", eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime()));
        }

        Band band = bandRepository.findById(eventCreateRequest.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", eventCreateRequest.getBandId())));
        EventCategory eventCategory = eventCategoryRepository.findById(eventCreateRequest.getEventCategoryId())
                .orElseThrow(() -> new NoSuchEventCategoryException(String.format("Event category with id=\"%s\" does not exist", eventCreateRequest.getEventCategoryId())));
        Room room = roomRepository.findById(eventCreateRequest.getRoomId())
                .orElseThrow(() -> new NoSuchRoomException(String.format("Room with id=\"%s\" does not exist", eventCreateRequest.getRoomId())));

        if (eventRepository.existsOverlappingEvent(eventCreateRequest.getBandId(), eventCreateRequest.getDate(), eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event with startTime=\"%s\" and endTime=\"%s\" overlaps other event", eventCreateRequest.getStartTime(), eventCreateRequest.getEndTime()));
        }

        if (!roomRepository.existsByBandIdAndId(eventCreateRequest.getBandId(), eventCreateRequest.getRoomId())) {
            throw new EventRoomConflictException(String.format("Room with id=\"%s\" does not belong to Band with id=\"%s\"", eventCreateRequest.getRoomId(), eventCreateRequest.getBandId()));
        }

        Event event = Event.builder()
                .date(eventCreateRequest.getDate())
                .startTime(eventCreateRequest.getStartTime())
                .endTime(eventCreateRequest.getEndTime())
                .band(band)
                .eventCategory(eventCategory)
                .room(room)
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
    public EventDTO modifyEventById(UUID eventId, EventUpdateRequest eventUpdateRequest) throws NoSuchEventException, NoSuchEventCategoryException, EventConflictException, NoSuchRoomException, EventRoomConflictException {
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
        Room room = roomRepository.findById(eventUpdateRequest.getRoomId())
                .orElseThrow(() -> new NoSuchRoomException(String.format("Room with id=\"%s\" does not exist", eventUpdateRequest.getRoomId())));

        if ((!eventUpdateRequest.getDate().equals(event.getDate()) || !eventUpdateRequest.getStartTime().equals(event.getStartTime()) || !eventUpdateRequest.getEndTime().equals(event.getEndTime())) && eventRepository.existsOverlappingEvent(event.getBand().getId(), eventUpdateRequest.getDate(), eventUpdateRequest.getStartTime(), eventUpdateRequest.getEndTime())) {
            throw new EventConflictException(String.format("Event with startTime=\"%s\" and endTime=\"%s\" overlaps other event", eventUpdateRequest.getStartTime(), eventUpdateRequest.getEndTime()));
        }

        if (!eventUpdateRequest.getRoomId().equals(event.getRoom().getId()) && !roomRepository.existsByBandIdAndId(event.getBand().getId(), eventUpdateRequest.getRoomId())) {
            throw new EventRoomConflictException(String.format("Room with id=\"%s\" does not belong to Band with id=\"%s\"", eventUpdateRequest.getRoomId(), event.getBand().getId()));
        }

        event.setDate(eventUpdateRequest.getDate());
        event.setStartTime(eventUpdateRequest.getStartTime());
        event.setEndTime(eventUpdateRequest.getEndTime());
        event.setEventCategory(eventCategory);
        event.setRoom(room);

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
