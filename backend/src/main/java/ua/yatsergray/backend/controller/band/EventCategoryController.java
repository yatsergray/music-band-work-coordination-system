package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.EventCategoryDTO;
import ua.yatsergray.backend.domain.request.band.EventCategoryCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventCategoryUpdateRequest;
import ua.yatsergray.backend.service.band.impl.EventCategoryServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event-categories")
public class EventCategoryController {
    private final EventCategoryServiceImpl eventCategoryService;

    @Autowired
    public EventCategoryController(EventCategoryServiceImpl eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EventCategoryDTO> createEventCategory(@Valid @RequestBody EventCategoryCreateRequest eventCategoryCreateRequest) {
        return ResponseEntity.ok(eventCategoryService.addEventCategory(eventCategoryCreateRequest));
    }

    @GetMapping("/{eventCategoryId}")
    public ResponseEntity<EventCategoryDTO> readEventCategoryById(@PathVariable("eventCategoryId") UUID eventCategoryId) {
        return eventCategoryService.getEventCategoryById(eventCategoryId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventCategoryDTO>> readAllEventCategories() {
        return ResponseEntity.ok(eventCategoryService.getAllEventCategories());
    }

    @SneakyThrows
    @PutMapping("/{eventCategoryId}")
    public ResponseEntity<EventCategoryDTO> updateEventCategoryById(@PathVariable("eventCategoryId") UUID eventCategoryId, @Valid @RequestBody EventCategoryUpdateRequest eventCategoryUpdateRequest) {
        return ResponseEntity.ok(eventCategoryService.modifyEventCategoryById(eventCategoryId, eventCategoryUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{eventCategoryId}")
    public ResponseEntity<Void> deleteEventCategoryById(@PathVariable("eventCategoryId") UUID eventCategoryId) {
        eventCategoryService.removeEventCategoryById(eventCategoryId);

        return ResponseEntity.ok().build();
    }
}
