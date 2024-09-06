package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.EventDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchEventCategoryException;
import ua.yatsergray.backend.exception.band.NoSuchEventException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {

    EventDTO addEvent(EventEditableDTO eventEditableDTO) throws NoSuchBandException, NoSuchEventCategoryException;

    Optional<EventDTO> getEventById(UUID id);

    List<EventDTO> getAllEvents();

    EventDTO modifyEventById(UUID id, EventEditableDTO eventEditableDTO) throws NoSuchEventException, NoSuchBandException, NoSuchEventCategoryException;

    void removeEventById(UUID id) throws NoSuchEventException;
}
