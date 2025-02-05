package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.EventDTO;
import ua.yatsergray.backend.v2.domain.request.EventCreateRequest;
import ua.yatsergray.backend.v2.domain.request.EventUpdateRequest;
import ua.yatsergray.backend.v2.exception.EventConflictException;
import ua.yatsergray.backend.v2.exception.NoSuchEventCategoryException;
import ua.yatsergray.backend.v2.exception.NoSuchEventException;
import ua.yatsergray.backend.v2.exception.NoSuchMusicBandException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {

    EventDTO addEvent(EventCreateRequest eventCreateRequest) throws EventConflictException, NoSuchMusicBandException, NoSuchEventCategoryException;

    Optional<EventDTO> getEventById(UUID eventId);

    List<EventDTO> getAllEvents();

    EventDTO modifyEventById(UUID eventId, EventUpdateRequest eventUpdateRequest) throws NoSuchEventException, EventConflictException, NoSuchEventCategoryException;

    void removeEventById(UUID eventId) throws NoSuchEventException;
}
