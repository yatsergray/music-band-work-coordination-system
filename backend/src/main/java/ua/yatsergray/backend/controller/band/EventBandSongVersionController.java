package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.EventBandSongVersionDTO;
import ua.yatsergray.backend.domain.request.band.EventBandSongVersionCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventBandSongVersionUpdateRequest;
import ua.yatsergray.backend.service.band.impl.EventBandSongVersionServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event-band-song-versions")
public class EventBandSongVersionController {
    private final EventBandSongVersionServiceImpl eventBandSongVersionService;

    @Autowired
    public EventBandSongVersionController(EventBandSongVersionServiceImpl eventBandSongVersionService) {
        this.eventBandSongVersionService = eventBandSongVersionService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EventBandSongVersionDTO> createEventBandSongVersion(@Valid @RequestBody EventBandSongVersionCreateRequest eventBandSongVersionCreateRequest) {
        return ResponseEntity.ok(eventBandSongVersionService.addEventBandSongVersion(eventBandSongVersionCreateRequest));
    }

    @GetMapping("/{eventBandSongVersionId}")
    public ResponseEntity<EventBandSongVersionDTO> readEventBandSongVersionById(@PathVariable("eventBandSongVersionId") UUID eventBandSongVersionId) {
        return eventBandSongVersionService.getEventBandSongVersionById(eventBandSongVersionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventBandSongVersionDTO>> readAllEventBandSongVersions() {
        return ResponseEntity.ok(eventBandSongVersionService.getAllEventBandSongVersions());
    }

    @SneakyThrows
    @PutMapping("/{eventBandSongVersionId}")
    public ResponseEntity<EventBandSongVersionDTO> updateEventBandSongVersionById(@PathVariable("eventBandSongVersionId") UUID eventBandSongVersionId, @Valid @RequestBody EventBandSongVersionUpdateRequest eventBandSongVersionUpdateRequest) {
        return ResponseEntity.ok(eventBandSongVersionService.modifyEventBandSongVersionById(eventBandSongVersionId, eventBandSongVersionUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{eventBandSongVersionId}")
    public ResponseEntity<Void> deleteEventBandSongVersionById(@PathVariable("eventBandSongVersionId") UUID eventBandSongVersionId) {
        eventBandSongVersionService.removeEventBandSongVersionById(eventBandSongVersionId);

        return ResponseEntity.ok().build();
    }
}
