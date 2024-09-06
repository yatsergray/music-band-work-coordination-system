package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventCategoryDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventCategoryEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchEventCategoryException;
import ua.yatsergray.backend.mapper.band.EventCategoryMapper;
import ua.yatsergray.backend.repository.band.EventCategoryRepository;
import ua.yatsergray.backend.service.band.EventCategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public EventCategoryDTO addEventCategory(EventCategoryEditableDTO eventCategoryEditableDTO) {
        return eventCategoryMapper.mapToEventCategoryDTO(eventCategoryRepository.save(eventCategoryMapper.mapToEventCategory(eventCategoryEditableDTO)));
    }

    @Override
    public Optional<EventCategoryDTO> getEventCategoryById(UUID id) {
        return eventCategoryRepository.findById(id).map(eventCategoryMapper::mapToEventCategoryDTO);
    }

    @Override
    public List<EventCategoryDTO> getAllEventCategories() {
        return eventCategoryMapper.mapAllToEventCategoryDTOList(eventCategoryRepository.findAll());
    }

    @Override
    public EventCategoryDTO modifyEventCategoryById(UUID id, EventCategoryEditableDTO eventCategoryEditableDTO) throws NoSuchEventCategoryException {
        return eventCategoryRepository.findById(id)
                .map(eventCategory -> {
                    eventCategory.setName(eventCategoryEditableDTO.getName());
                    eventCategory.setType(eventCategoryEditableDTO.getType());

                    return eventCategoryMapper.mapToEventCategoryDTO(eventCategoryRepository.save(eventCategory));
                })
                .orElseThrow(() -> new NoSuchEventCategoryException(String.format("Event category does not exist with id=%s", id)));
    }

    @Override
    public void removeEventCategoryById(UUID id) throws NoSuchEventCategoryException {
        if (!eventCategoryRepository.existsById(id)) {
            throw new NoSuchEventCategoryException(String.format("Event category does not exist with id=%s", id));
        }

        eventCategoryRepository.deleteById(id);
    }
}
