package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.EventSongDTO;
import ua.yatsergray.backend.v2.domain.request.EventSongCreateRequest;
import ua.yatsergray.backend.v2.domain.request.EventSongUpdateRequest;
import ua.yatsergray.backend.v2.service.EventSongService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event-songs")
public class EventSongController {
    private final EventSongService eventSongService;

    @Autowired
    public EventSongController(EventSongService eventSongService) {
        this.eventSongService = eventSongService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EventSongDTO> createEventSong(@Valid @RequestBody EventSongCreateRequest eventSongCreateRequest) {
        return ResponseEntity.ok(eventSongService.addEventSong(eventSongCreateRequest));
    }

    @GetMapping("/{eventSongId}")
    public ResponseEntity<EventSongDTO> readEventSongById(@PathVariable("eventSongId") UUID eventSongId) {
        return eventSongService.getEventSongById(eventSongId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Page<EventSongDTO>> readAllEventSongsByEventIdAndPageAndSize(@PathVariable("eventId") UUID eventId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(eventSongService.getAllEventSongsByEventIdAndPageAndSize(eventId, page, size));
    }

    @SneakyThrows
    @PutMapping("/{eventSongId}")
    public ResponseEntity<EventSongDTO> updateEventSongById(@PathVariable("eventSongId") UUID eventSongId, @Valid @RequestBody EventSongUpdateRequest eventSongUpdateRequest) {
        return ResponseEntity.ok(eventSongService.modifyEventSongById(eventSongId, eventSongUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{eventSongId}")
    public ResponseEntity<Void> deleteEventSongById(@PathVariable("eventSongId") UUID eventSongId) {
        eventSongService.removeEventSongById(eventSongId);

        return ResponseEntity.ok().build();
    }
}
