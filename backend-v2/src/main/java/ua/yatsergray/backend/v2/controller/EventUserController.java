package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.EventUserDTO;
import ua.yatsergray.backend.v2.domain.request.EventUserCreateRequest;
import ua.yatsergray.backend.v2.domain.request.EventUserUpdateRequest;
import ua.yatsergray.backend.v2.service.EventUserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event-users")
public class EventUserController {
    private final EventUserService eventUserService;

    @Autowired
    public EventUserController(EventUserService eventUserService) {
        this.eventUserService = eventUserService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EventUserDTO> createEventUser(@Valid @RequestBody EventUserCreateRequest eventUserCreateRequest) {
        return ResponseEntity.ok(eventUserService.addEventUser(eventUserCreateRequest));
    }

    @GetMapping("/{eventUserId}")
    public ResponseEntity<EventUserDTO> readEventUserById(@PathVariable("eventUserId") UUID eventUserId) {
        return eventUserService.getEventUserById(eventUserId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Page<EventUserDTO>> readAllEventUsersByEventIdAndPageAndSize(@PathVariable("eventId") UUID eventId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(eventUserService.getAllEventUsersByEventIdAndPageAndSize(eventId, page, size));
    }

    @SneakyThrows
    @PutMapping("/{eventUserId}")
    public ResponseEntity<EventUserDTO> updateEventUserById(@PathVariable("eventUserId") UUID eventUserId, @Valid @RequestBody EventUserUpdateRequest eventUserUpdateRequest) {
        return ResponseEntity.ok(eventUserService.modifyEventUserById(eventUserId, eventUserUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{eventUserId}")
    public ResponseEntity<Void> deleteEventUserById(@PathVariable("eventUserId") UUID eventUserId) {
        eventUserService.removeEventUserById(eventUserId);

        return ResponseEntity.ok().build();
    }
}
