package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.EventDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventEditableDTO;
import ua.yatsergray.backend.service.band.impl.EventServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventServiceImpl eventService;

    @Autowired
    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventEditableDTO eventEditableDTO) {
        return ResponseEntity.ok(eventService.addEvent(eventEditableDTO));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> readEventById(@PathVariable("eventId") UUID eventId) {
        return eventService.getEventById(eventId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> readAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @SneakyThrows
    @PutMapping("/{eventId}")
    public ResponseEntity<EventDTO> updateEventById(@PathVariable("eventId") UUID eventId, @Valid @RequestBody EventEditableDTO eventEditableDTO) {
        return ResponseEntity.ok(eventService.modifyEventById(eventId, eventEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEventById(@PathVariable("eventId") UUID eventId) {
        eventService.removeEventById(eventId);

        return ResponseEntity.ok().build();
    }
}
