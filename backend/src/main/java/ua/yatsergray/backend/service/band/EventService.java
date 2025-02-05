package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.EventDTO;
import ua.yatsergray.backend.domain.request.band.EventCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventUpdateRequest;
import ua.yatsergray.backend.domain.type.band.EventStatusType;
import ua.yatsergray.backend.exception.band.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {

    EventDTO addEvent(EventCreateRequest eventCreateRequest) throws NoSuchBandException, NoSuchEventCategoryException, EventConflictException, NoSuchRoomException, EventRoomConflictException;

    Optional<EventDTO> getEventById(UUID eventId);

    List<EventDTO> getAllEvents();

    EventDTO modifyEventById(UUID eventId, EventUpdateRequest eventUpdateRequest) throws NoSuchEventException, NoSuchEventCategoryException, EventConflictException, NoSuchRoomException, EventRoomConflictException;

    void removeEventById(UUID eventId) throws NoSuchEventException;

    EventDTO changeEventStatus(UUID eventId, EventStatusType eventStatusType) throws NoSuchEventException, NoSuchEventStatusException;
}
