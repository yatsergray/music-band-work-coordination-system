package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.EventCategoryDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventCategoryEditableDTO;
import ua.yatsergray.backend.service.band.impl.EventCategoryServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event-categories")
public class EventCategoryController {
    private final EventCategoryServiceImpl eventCategoryService;

    @Autowired
    public EventCategoryController(EventCategoryServiceImpl eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
    }

    @PostMapping
    public ResponseEntity<EventCategoryDTO> createEventCategory(@RequestBody EventCategoryEditableDTO eventCategoryEditableDTO) {
        return ResponseEntity.ok(eventCategoryService.addEventCategory(eventCategoryEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventCategoryDTO> readEventCategoryById(@PathVariable("id") UUID id) {
        return eventCategoryService.getEventCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventCategoryDTO>> readAllEventCategories() {
        return ResponseEntity.ok(eventCategoryService.getAllEventCategories());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<EventCategoryDTO> updateEventCategoryById(@PathVariable("id") UUID id, @RequestBody EventCategoryEditableDTO eventCategoryEditableDTO) {
        return ResponseEntity.ok(eventCategoryService.modifyEventCategoryById(id, eventCategoryEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventCategoryById(@PathVariable("id") UUID id) {
        eventCategoryService.removeEventCategoryById(id);

        return ResponseEntity.ok().build();
    }
}
