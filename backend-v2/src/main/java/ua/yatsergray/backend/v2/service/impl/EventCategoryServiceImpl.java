package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.EventCategoryDTO;
import ua.yatsergray.backend.v2.mapper.EventCategoryMapper;
import ua.yatsergray.backend.v2.repository.EventCategoryRepository;
import ua.yatsergray.backend.v2.service.EventCategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class EventCategoryServiceImpl implements EventCategoryService {
    private final EventCategoryMapper eventCategoryMapper;
    private final EventCategoryRepository eventCategoryRepository;

    @Autowired
    public EventCategoryServiceImpl(EventCategoryMapper eventCategoryMapper, EventCategoryRepository eventCategoryRepository) {
        this.eventCategoryMapper = eventCategoryMapper;
        this.eventCategoryRepository = eventCategoryRepository;
    }

    @Override
    public Optional<EventCategoryDTO> getEventCategoryById(UUID eventCategoryId) {
        return eventCategoryRepository.findById(eventCategoryId).map(eventCategoryMapper::mapToEventCategoryDTO);
    }

    @Override
    public List<EventCategoryDTO> getAllEventCategories() {
        return eventCategoryMapper.mapAllToEventCategoryDTOList(eventCategoryRepository.findAll());
    }
}
