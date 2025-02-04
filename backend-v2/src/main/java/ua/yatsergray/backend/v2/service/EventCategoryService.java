package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.EventCategoryDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventCategoryService {

    Optional<EventCategoryDTO> getEventCategoryById(UUID eventCategoryId);

    List<EventCategoryDTO> getAllEventCategories();
}
