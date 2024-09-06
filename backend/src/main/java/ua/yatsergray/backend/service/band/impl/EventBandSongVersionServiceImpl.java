package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventBandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventBandSongVersionEditableDTO;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;
import ua.yatsergray.backend.domain.entity.band.Event;
import ua.yatsergray.backend.domain.entity.band.EventBandSongVersion;
import ua.yatsergray.backend.exception.band.NoSuchBandSongVersionException;
import ua.yatsergray.backend.exception.band.NoSuchEventBandSongVersionException;
import ua.yatsergray.backend.exception.band.NoSuchEventException;
import ua.yatsergray.backend.mapper.band.EventBandSongVersionMapper;
import ua.yatsergray.backend.repository.band.BandSongVersionRepository;
import ua.yatsergray.backend.repository.band.EventBandSongVersionRepository;
import ua.yatsergray.backend.repository.band.EventRepository;
import ua.yatsergray.backend.service.band.EventBandSongVersionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public EventBandSongVersionDTO addEventBandSongVersion(EventBandSongVersionEditableDTO eventBandSongVersionEditableDTO) throws NoSuchEventException, NoSuchBandSongVersionException {
        Event event = eventRepository.findById(eventBandSongVersionEditableDTO.getEventUUID())
                .orElseThrow(() -> new NoSuchEventException(String.format("Event does not exist with id=%s", eventBandSongVersionEditableDTO.getEventUUID())));
        BandSongVersion bandSongVersion = bandSongVersionRepository.findById(eventBandSongVersionEditableDTO.getBandSongVersionUUID())
                .orElseThrow(() -> new NoSuchBandSongVersionException(String.format("Band song version does not exist with id=%s", eventBandSongVersionEditableDTO.getBandSongVersionUUID())));

        EventBandSongVersion eventBandSongVersion = eventBandSongVersionMapper.mapToEventBandSongVersion(eventBandSongVersionEditableDTO);

        eventBandSongVersion.setEvent(event);
        eventBandSongVersion.setBandSongVersion(bandSongVersion);

        return eventBandSongVersionMapper.mapToEventBandSongVersionDTO(eventBandSongVersionRepository.save(eventBandSongVersion));
    }

    @Override
    public Optional<EventBandSongVersionDTO> getEventBandSongVersionById(UUID id) {
        return eventBandSongVersionRepository.findById(id).map(eventBandSongVersionMapper::mapToEventBandSongVersionDTO);
    }

    @Override
    public List<EventBandSongVersionDTO> getAllEventBandSongVersions() {
        return eventBandSongVersionMapper.mapAllToEventBandSongVersionDTOList(eventBandSongVersionRepository.findAll());
    }

    @Override
    public EventBandSongVersionDTO modifyEventBandSongVersionById(UUID id, EventBandSongVersionEditableDTO eventBandSongVersionEditableDTO) throws NoSuchEventBandSongVersionException, NoSuchEventException, NoSuchBandSongVersionException {
        EventBandSongVersion eventBandSongVersion = eventBandSongVersionRepository.findById(id)
                .orElseThrow(() -> new NoSuchEventBandSongVersionException(String.format("Event band song version does not exist with id=%s", id)));
        Event event = eventRepository.findById(eventBandSongVersionEditableDTO.getEventUUID())
                .orElseThrow(() -> new NoSuchEventException(String.format("Event does not exist with id=%s", eventBandSongVersionEditableDTO.getEventUUID())));
        BandSongVersion bandSongVersion = bandSongVersionRepository.findById(eventBandSongVersionEditableDTO.getBandSongVersionUUID())
                .orElseThrow(() -> new NoSuchBandSongVersionException(String.format("Band song version does not exist with id=%s", eventBandSongVersionEditableDTO.getBandSongVersionUUID())));

        eventBandSongVersion.setSequenceNumber(eventBandSongVersionEditableDTO.getSequenceNumber());
        eventBandSongVersion.setEvent(event);
        eventBandSongVersion.setBandSongVersion(bandSongVersion);

        return eventBandSongVersionMapper.mapToEventBandSongVersionDTO(eventBandSongVersionRepository.save(eventBandSongVersion));
    }

    @Override
    public void removeEventBandSongVersionById(UUID id) throws NoSuchEventBandSongVersionException {
        if (!eventBandSongVersionRepository.existsById(id)) {
            throw new NoSuchEventBandSongVersionException(String.format("Event band song version does not exist with id=%s", id));
        }

        eventBandSongVersionRepository.deleteById(id);
    }
}
