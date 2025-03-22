package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.EventSongDTO;
import ua.yatsergray.backend.v2.domain.entity.Event;
import ua.yatsergray.backend.v2.domain.entity.EventSong;
import ua.yatsergray.backend.v2.domain.entity.Song;
import ua.yatsergray.backend.v2.domain.request.EventSongCreateRequest;
import ua.yatsergray.backend.v2.domain.request.EventSongUpdateRequest;
import ua.yatsergray.backend.v2.exception.*;
import ua.yatsergray.backend.v2.mapper.EventSongMapper;
import ua.yatsergray.backend.v2.repository.EventRepository;
import ua.yatsergray.backend.v2.repository.EventSongRepository;
import ua.yatsergray.backend.v2.repository.SongRepository;
import ua.yatsergray.backend.v2.service.EventSongService;

import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class EventSongServiceImpl implements EventSongService {
    private final EventSongMapper eventSongMapper;
    private final EventSongRepository eventSongRepository;
    private final EventRepository eventRepository;
    private final SongRepository songRepository;

    @Autowired
    public EventSongServiceImpl(EventSongMapper eventSongMapper, EventSongRepository eventSongRepository, EventRepository eventRepository, SongRepository songRepository) {
        this.eventSongMapper = eventSongMapper;
        this.eventSongRepository = eventSongRepository;
        this.eventRepository = eventRepository;
        this.songRepository = songRepository;
    }

    @Override
    public EventSongDTO addEventSong(EventSongCreateRequest eventSongCreateRequest) throws NoSuchEventException, NoSuchSongException, EventSongConflictException, EventSongAlreadyExistsException {
        Event event = eventRepository.findById(eventSongCreateRequest.getEventId())
                .orElseThrow(() -> new NoSuchEventException(String.format("Event with id=\"%s\" does not exist", eventSongCreateRequest.getEventId())));
        Song song = songRepository.findById(eventSongCreateRequest.getSongId())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", eventSongCreateRequest.getSongId())));

        if (!event.getMusicBand().getSongs().contains(song)) {
            throw new EventSongConflictException(String.format("Song with id=\"%s\" does not belong to the Music band with id=\"%s\"", eventSongCreateRequest.getSongId(), eventSongCreateRequest.getEventId()));
        }

        if (eventSongRepository.existsByEventIdAndSequenceNumber(eventSongCreateRequest.getEventId(), eventSongCreateRequest.getSequenceNumber())) {
            throw new EventSongAlreadyExistsException(String.format("Event song with eventId=\"%s\" and sequenceNumber=\"%s\" already exists", eventSongCreateRequest.getEventId(), eventSongCreateRequest.getSequenceNumber()));
        }

        EventSong eventSong = EventSong.builder()
                .sequenceNumber(eventSongCreateRequest.getSequenceNumber())
                .event(event)
                .song(song)
                .build();

        return eventSongMapper.mapToEventSongDTO(eventSongRepository.save(eventSong));
    }

    @Override
    public Optional<EventSongDTO> getEventSongById(UUID eventSongId) {
        return eventSongRepository.findById(eventSongId).map(eventSongMapper::mapToEventSongDTO);
    }

    @Override
    public Page<EventSongDTO> getAllEventSongsByEventIdAndPageAndSize(UUID eventId, int page, int size) {
        return eventSongRepository.findAllByEventId(eventId, PageRequest.of(page, size, Sort.by("sequenceNumber").ascending())).map(eventSongMapper::mapToEventSongDTO);
    }

    @Override
    public EventSongDTO modifyEventSongById(UUID eventSongId, EventSongUpdateRequest eventSongUpdateRequest) throws NoSuchEventSongException, EventSongAlreadyExistsException {
        EventSong eventSong = eventSongRepository.findById(eventSongId)
                .orElseThrow(() -> new NoSuchEventSongException(String.format("Event song with id=\"%s\" does not exist", eventSongId)));

        if (!eventSongUpdateRequest.getSequenceNumber().equals(eventSong.getSequenceNumber()) && eventSongRepository.existsByEventIdAndSequenceNumber(eventSong.getEvent().getId(), eventSongUpdateRequest.getSequenceNumber())) {
            throw new EventSongAlreadyExistsException(String.format("Event song with eventId=\"%s\" and sequenceNumber=\"%s\" already exists", eventSong.getEvent().getId(), eventSongUpdateRequest.getSequenceNumber()));
        }

        eventSong.setSequenceNumber(eventSongUpdateRequest.getSequenceNumber());

        return eventSongMapper.mapToEventSongDTO(eventSongRepository.save(eventSong));
    }

    @Override
    public void removeEventSongById(UUID eventBandSongVersionId) throws NoSuchEventSongException {
        if (!eventSongRepository.existsById(eventBandSongVersionId)) {
            throw new NoSuchEventSongException(String.format("Event song with id=\"%s\" does not exist", eventBandSongVersionId));
        }

        eventSongRepository.deleteById(eventBandSongVersionId);
    }
}
