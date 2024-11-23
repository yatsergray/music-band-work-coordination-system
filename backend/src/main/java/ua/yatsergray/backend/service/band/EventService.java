package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.EventDTO;
import ua.yatsergray.backend.domain.request.band.EventCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventUpdateRequest;
import ua.yatsergray.backend.exception.band.EventConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchEventCategoryException;
import ua.yatsergray.backend.exception.band.NoSuchEventException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {

    EventDTO addEvent(EventCreateRequest eventCreateRequest) throws NoSuchBandException, NoSuchEventCategoryException, EventConflictException;

    Optional<EventDTO> getEventById(UUID eventId);

    List<EventDTO> getAllEvents();

    EventDTO modifyEventById(UUID eventId, EventUpdateRequest eventUpdateRequest) throws NoSuchEventException, NoSuchEventCategoryException, EventConflictException;

    void removeEventById(UUID eventId) throws NoSuchEventException;
}
