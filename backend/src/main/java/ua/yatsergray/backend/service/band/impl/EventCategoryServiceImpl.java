package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.EventCategoryDTO;
import ua.yatsergray.backend.domain.entity.band.EventCategory;
import ua.yatsergray.backend.domain.request.band.EventCategoryCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventCategoryUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.EventCategoryAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchEventCategoryException;
import ua.yatsergray.backend.mapper.band.EventCategoryMapper;
import ua.yatsergray.backend.repository.band.EventCategoryRepository;
import ua.yatsergray.backend.repository.band.EventRepository;
import ua.yatsergray.backend.service.band.EventCategoryService;

import java.util.List;
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
    public EventCategoryDTO addEventCategory(EventCategoryCreateRequest eventCategoryCreateRequest) throws EventCategoryAlreadyExistsException {
        if (eventCategoryRepository.existsByName(eventCategoryCreateRequest.getName())) {
            throw new EventCategoryAlreadyExistsException(String.format("Event category with name=\"%s\" already exists", eventCategoryCreateRequest.getName()));
        }

        if (eventCategoryRepository.existsByType(eventCategoryCreateRequest.getType())) {
            throw new EventCategoryAlreadyExistsException(String.format("Event category with type=\"%s\" already exists", eventCategoryCreateRequest.getType()));
        }

        EventCategory eventCategory = EventCategory.builder()
                .name(eventCategoryCreateRequest.getName())
                .type(eventCategoryCreateRequest.getType())
                .build();

        return eventCategoryMapper.mapToEventCategoryDTO(eventCategoryRepository.save(eventCategory));
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
    public EventCategoryDTO modifyEventCategoryById(UUID eventCategoryId, EventCategoryUpdateRequest eventCategoryUpdateRequest) throws NoSuchEventCategoryException, EventCategoryAlreadyExistsException {
        EventCategory eventCategory = eventCategoryRepository.findById(eventCategoryId)
                .orElseThrow(() -> new NoSuchEventCategoryException(String.format("Event category with id=\"%s\" does not exist", eventCategoryId)));

        if (!eventCategoryUpdateRequest.getName().equals(eventCategory.getName()) && eventCategoryRepository.existsByName(eventCategoryUpdateRequest.getName())) {
            throw new EventCategoryAlreadyExistsException(String.format("Event category with name=\"%s\" already exists", eventCategoryUpdateRequest.getName()));
        }

        eventCategory.setName(eventCategoryUpdateRequest.getName());

        return eventCategoryMapper.mapToEventCategoryDTO(eventCategoryRepository.save(eventCategory));
    }

    @Override
    public void removeEventCategoryById(UUID eventCategoryId) throws NoSuchEventCategoryException, ChildEntityExistsException {
        if (!eventCategoryRepository.existsById(eventCategoryId)) {
            throw new NoSuchEventCategoryException(String.format("Event category with id=\"%s\" does not exist", eventCategoryId));
        }

        checkIfEventCategoryHasChildEntity(eventCategoryId);

        eventCategoryRepository.deleteById(eventCategoryId);
    }

    private void checkIfEventCategoryHasChildEntity(UUID eventCategoryId) throws ChildEntityExistsException {
        long eventCategoryChildEntityAmount = eventRepository.countByEventCategoryId(eventCategoryId);

        if (eventCategoryChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Event(s) depend(s) on the Event category with id=%s", eventCategoryChildEntityAmount, eventCategoryId));
        }
    }
}
