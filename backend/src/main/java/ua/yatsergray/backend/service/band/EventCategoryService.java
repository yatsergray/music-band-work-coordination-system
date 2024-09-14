package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.EventCategoryDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventCategoryEditableDTO;
import ua.yatsergray.backend.exception.band.EventCategoryAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchEventCategoryException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventCategoryService {

    EventCategoryDTO addEventCategory(EventCategoryEditableDTO eventCategoryEditableDTO) throws EventCategoryAlreadyExistsException;

    Optional<EventCategoryDTO> getEventCategoryById(UUID eventCategoryId);

    List<EventCategoryDTO> getAllEventCategories();

    EventCategoryDTO modifyEventCategoryById(UUID eventCategoryId, EventCategoryEditableDTO eventCategoryEditableDTO) throws NoSuchEventCategoryException, EventCategoryAlreadyExistsException;

    void removeEventCategoryById(UUID eventCategoryId) throws NoSuchEventCategoryException;
}
