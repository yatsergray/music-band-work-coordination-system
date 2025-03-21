package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.EventDTO;
import ua.yatsergray.backend.v2.domain.request.EventCreateRequest;
import ua.yatsergray.backend.v2.domain.request.EventUpdateRequest;
import ua.yatsergray.backend.v2.service.EventService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventCreateRequest eventCreateRequest) {
        return ResponseEntity.ok(eventService.addEvent(eventCreateRequest));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> readEventById(@PathVariable("eventId") UUID eventId) {
        return eventService.getEventById(eventId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping
//    public ResponseEntity<List<EventDTO>> readAllEvents() {
//        return ResponseEntity.ok(eventService.getAllEvents());
//    }

    @GetMapping
    public ResponseEntity<Page<EventDTO>> readAllEventsByPageAndSize(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(eventService.getAllEventsByPageAndSize(page, size));
    }


    @SneakyThrows
    @PutMapping("/{eventId}")
    public ResponseEntity<EventDTO> updateEventById(@PathVariable("eventId") UUID eventId, @Valid @RequestBody EventUpdateRequest eventUpdateRequest) {
        return ResponseEntity.ok(eventService.modifyEventById(eventId, eventUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEventById(@PathVariable("eventId") UUID eventId) {
        eventService.removeEventById(eventId);

        return ResponseEntity.ok().build();
    }
}
