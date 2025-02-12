package ua.yatsergray.backend.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.yatsergray.backend.v2.domain.dto.EventCategoryDTO;
import ua.yatsergray.backend.v2.service.EventCategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event-categories")
public class EventCategoryController {
    private final EventCategoryService eventCategoryService;

    @Autowired
    public EventCategoryController(EventCategoryService eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
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
}
