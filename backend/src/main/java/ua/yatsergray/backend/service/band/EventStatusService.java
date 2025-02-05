package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.EventStatusDTO;
import ua.yatsergray.backend.domain.request.band.EventStatusCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventStatusUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.EventStatusAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchEventStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventStatusService {

    EventStatusDTO addEventStatus(EventStatusCreateRequest eventStatusCreateRequest) throws EventStatusAlreadyExistsException;

    Optional<EventStatusDTO> getEventStatusById(UUID eventStatusId);

    List<EventStatusDTO> getAllEventStatuses();

    EventStatusDTO modifyEventStatusById(UUID eventStatusId, EventStatusUpdateRequest eventStatusUpdateRequest) throws NoSuchEventStatusException, EventStatusAlreadyExistsException;

    void removeEventStatusById(UUID eventStatusId) throws NoSuchEventStatusException, ChildEntityExistsException;
}
