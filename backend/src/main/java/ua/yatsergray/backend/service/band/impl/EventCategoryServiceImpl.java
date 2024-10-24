package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventCategoryDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventCategoryEditableDTO;
import ua.yatsergray.backend.domain.entity.band.EventCategory;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.EventCategoryAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchEventCategoryException;
import ua.yatsergray.backend.mapper.band.EventCategoryMapper;
import ua.yatsergray.backend.repository.band.EventCategoryRepository;
import ua.yatsergray.backend.repository.band.EventRepository;
import ua.yatsergray.backend.service.band.EventCategoryService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class EventCategoryServiceImpl implements EventCategoryService {
    private final EventCategoryMapper eventCategoryMapper;
    private final EventCategoryRepository eventCategoryRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventCategoryServiceImpl(EventCategoryMapper eventCategoryMapper, EventCategoryRepository eventCategoryRepository, EventRepository eventRepository) {
        this.eventCategoryMapper = eventCategoryMapper;
        this.eventCategoryRepository = eventCategoryRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public EventCategoryDTO addEventCategory(EventCategoryEditableDTO eventCategoryEditableDTO) throws EventCategoryAlreadyExistsException {
        return eventCategoryMapper.mapToEventCategoryDTO(eventCategoryRepository.save(configureEventCategory(new EventCategory(), eventCategoryEditableDTO)));
    }

    @Override
    public Optional<EventCategoryDTO> getEventCategoryById(UUID eventCategoryId) {
        return eventCategoryRepository.findById(eventCategoryId).map(eventCategoryMapper::mapToEventCategoryDTO);
    }

    @Override
    public List<EventCategoryDTO> getAllEventCategories() {
        return eventCategoryMapper.mapAllToEventCategoryDTOList(eventCategoryRepository.findAll());
    }

    @Override
    public EventCategoryDTO modifyEventCategoryById(UUID eventCategoryId, EventCategoryEditableDTO eventCategoryEditableDTO) throws NoSuchEventCategoryException, EventCategoryAlreadyExistsException {
        EventCategory eventCategory = eventCategoryRepository.findById(eventCategoryId)
                .orElseThrow(() -> new NoSuchEventCategoryException(String.format("Event category with id=\"%s\" does not exist", eventCategoryId)));

        return eventCategoryMapper.mapToEventCategoryDTO(eventCategoryRepository.save(configureEventCategory(eventCategory, eventCategoryEditableDTO)));
    }

    @Override
    public void removeEventCategoryById(UUID eventCategoryId) throws NoSuchEventCategoryException, ChildEntityExistsException {
        if (!eventCategoryRepository.existsById(eventCategoryId)) {
            throw new NoSuchEventCategoryException(String.format("Event category with id=\"%s\" does not exist", eventCategoryId));
        }

        checkIfEventCategoryHasChildEntity(eventCategoryId);

        eventCategoryRepository.deleteById(eventCategoryId);
    }

    private EventCategory configureEventCategory(EventCategory eventCategory, EventCategoryEditableDTO eventCategoryEditableDTO) throws EventCategoryAlreadyExistsException {
        if (Objects.isNull(eventCategory.getId())) {
            if (eventCategoryRepository.existsByName(eventCategoryEditableDTO.getName())) {
                throw new EventCategoryAlreadyExistsException(String.format("Event category with name=\"%s\" already exists", eventCategoryEditableDTO.getName()));
            }

            if (eventCategoryRepository.existsByType(eventCategoryEditableDTO.getType())) {
                throw new EventCategoryAlreadyExistsException(String.format("Event category with type=\"%s\" already exists", eventCategoryEditableDTO.getType()));
            }
        } else {
            if (!eventCategoryEditableDTO.getName().equals(eventCategory.getName()) && eventCategoryRepository.existsByName(eventCategoryEditableDTO.getName())) {
                throw new EventCategoryAlreadyExistsException(String.format("Event category with name=\"%s\" already exists", eventCategoryEditableDTO.getName()));
            }

            if (!eventCategoryEditableDTO.getType().equals(eventCategory.getType()) && eventCategoryRepository.existsByType(eventCategoryEditableDTO.getType())) {
                throw new EventCategoryAlreadyExistsException(String.format("Event category with type=\"%s\" already exists", eventCategoryEditableDTO.getType()));
            }
        }

        eventCategory.setName(eventCategoryEditableDTO.getName());
        eventCategory.setType(eventCategoryEditableDTO.getType());

        return eventCategory;
    }

    private void checkIfEventCategoryHasChildEntity(UUID eventCategoryId) throws ChildEntityExistsException {
        long eventCategoryChildEntityAmount = eventRepository.countByEventCategoryId(eventCategoryId);

        if (eventCategoryChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Event(s) depend(s) on the Event category with id=%s", eventCategoryChildEntityAmount, eventCategoryId));
        }
    }
}
