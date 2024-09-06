package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.EventCategoryDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventCategoryEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchEventCategoryException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventCategoryService {

    EventCategoryDTO addEventCategory(EventCategoryEditableDTO eventCategoryEditableDTO);

    Optional<EventCategoryDTO> getEventCategoryById(UUID id);

    List<EventCategoryDTO> getAllEventCategories();

    EventCategoryDTO modifyEventCategoryById(UUID id, EventCategoryEditableDTO eventCategoryEditableDTO) throws NoSuchEventCategoryException;

    void removeEventCategoryById(UUID id) throws NoSuchEventCategoryException;
}
