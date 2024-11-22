package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventBandSongVersionDTO;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;
import ua.yatsergray.backend.domain.entity.band.Event;
import ua.yatsergray.backend.domain.entity.band.EventBandSongVersion;
import ua.yatsergray.backend.domain.request.band.EventBandSongVersionCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventBandSongVersionUpdateRequest;
import ua.yatsergray.backend.exception.band.*;
import ua.yatsergray.backend.mapper.band.EventBandSongVersionMapper;
import ua.yatsergray.backend.repository.band.BandSongVersionRepository;
import ua.yatsergray.backend.repository.band.EventBandSongVersionRepository;
import ua.yatsergray.backend.repository.band.EventRepository;
import ua.yatsergray.backend.service.band.EventBandSongVersionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class EventBandSongVersionServiceImpl implements EventBandSongVersionService {

    private final EventBandSongVersionMapper eventBandSongVersionMapper;
    private final EventBandSongVersionRepository eventBandSongVersionRepository;
    private final EventRepository eventRepository;
    private final BandSongVersionRepository bandSongVersionRepository;

    @Autowired
    public EventBandSongVersionServiceImpl(EventBandSongVersionMapper eventBandSongVersionMapper, EventBandSongVersionRepository eventBandSongVersionRepository, EventRepository eventRepository, BandSongVersionRepository bandSongVersionRepository) {
        this.eventBandSongVersionMapper = eventBandSongVersionMapper;
        this.eventBandSongVersionRepository = eventBandSongVersionRepository;
        this.eventRepository = eventRepository;
        this.bandSongVersionRepository = bandSongVersionRepository;
    }

    @Override
    public EventBandSongVersionDTO addEventBandSongVersion(EventBandSongVersionCreateRequest eventBandSongVersionCreateRequest) throws NoSuchEventException, NoSuchBandSongVersionException, EventBandSongVersionConflictException, EventBandSongVersionAlreadyExistsException {
        Event event = eventRepository.findById(eventBandSongVersionCreateRequest.getEventId())
                .orElseThrow(() -> new NoSuchEventException(String.format("Event with id=\"%s\" does not exist", eventBandSongVersionCreateRequest.getEventId())));
        BandSongVersion bandSongVersion = bandSongVersionRepository.findById(eventBandSongVersionCreateRequest.getBandSongVersionId())
                .orElseThrow(() -> new NoSuchBandSongVersionException(String.format("Band song version with id=\"%s\" does not exist", eventBandSongVersionCreateRequest.getBandSongVersionId())));

        if (!event.getBand().getBandSongVersions().contains(bandSongVersion)) {
            throw new EventBandSongVersionConflictException(String.format("Band song version with id=\"%s\" does not belong to the Band with id=\"%s\"", eventBandSongVersionCreateRequest.getBandSongVersionId(), eventBandSongVersionCreateRequest.getEventId()));
        }

        if (eventBandSongVersionRepository.existsByEventIdAndSequenceNumber(eventBandSongVersionCreateRequest.getEventId(), eventBandSongVersionCreateRequest.getSequenceNumber())) {
            throw new EventBandSongVersionAlreadyExistsException(String.format("Event band song version with eventId=\"%s\" and sequenceNumber=\"%s\" already exists", eventBandSongVersionCreateRequest.getEventId(), eventBandSongVersionCreateRequest.getSequenceNumber()));
        }

        EventBandSongVersion eventBandSongVersion = EventBandSongVersion.builder()
                .sequenceNumber(eventBandSongVersionCreateRequest.getSequenceNumber())
                .event(event)
                .bandSongVersion(bandSongVersion)
                .build();

        return eventBandSongVersionMapper.mapToEventBandSongVersionDTO(eventBandSongVersionRepository.save(eventBandSongVersion));
    }

    @Override
    public Optional<EventBandSongVersionDTO> getEventBandSongVersionById(UUID eventBandSongVersionId) {
        return eventBandSongVersionRepository.findById(eventBandSongVersionId).map(eventBandSongVersionMapper::mapToEventBandSongVersionDTO);
    }

    @Override
    public List<EventBandSongVersionDTO> getAllEventBandSongVersions() {
        return eventBandSongVersionMapper.mapAllToEventBandSongVersionDTOList(eventBandSongVersionRepository.findAll());
    }

    @Override
    public EventBandSongVersionDTO modifyEventBandSongVersionById(UUID eventBandSongVersionId, EventBandSongVersionUpdateRequest eventBandSongVersionUpdateRequest) throws NoSuchEventBandSongVersionException, EventBandSongVersionAlreadyExistsException {
        EventBandSongVersion eventBandSongVersion = eventBandSongVersionRepository.findById(eventBandSongVersionId)
                .orElseThrow(() -> new NoSuchEventBandSongVersionException(String.format("Event band song version with id=\"%s\" does not exist", eventBandSongVersionId)));

        if (!eventBandSongVersionUpdateRequest.getSequenceNumber().equals(eventBandSongVersion.getSequenceNumber()) && eventBandSongVersionRepository.existsByEventIdAndSequenceNumber(eventBandSongVersion.getEvent().getId(), eventBandSongVersionUpdateRequest.getSequenceNumber())) {
            throw new EventBandSongVersionAlreadyExistsException(String.format("Event band song version with eventId=\"%s\" and sequenceNumber=\"%s\" already exists", eventBandSongVersion.getEvent().getId(), eventBandSongVersionUpdateRequest.getSequenceNumber()));
        }

        eventBandSongVersion.setSequenceNumber(eventBandSongVersionUpdateRequest.getSequenceNumber());

        return eventBandSongVersionMapper.mapToEventBandSongVersionDTO(eventBandSongVersionRepository.save(eventBandSongVersion));
    }

    @Override
    public void removeEventBandSongVersionById(UUID eventBandSongVersionId) throws NoSuchEventBandSongVersionException {
        if (!eventBandSongVersionRepository.existsById(eventBandSongVersionId)) {
            throw new NoSuchEventBandSongVersionException(String.format("Event band song version with id=\"%s\" does not exist", eventBandSongVersionId));
        }

        eventBandSongVersionRepository.deleteById(eventBandSongVersionId);
    }
}
