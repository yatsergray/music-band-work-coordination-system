package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.EventBandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventBandSongVersionEditableDTO;
import ua.yatsergray.backend.service.band.impl.EventBandSongVersionServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event-band-song-versions")
public class EventBandSongVersionController {
    private final EventBandSongVersionServiceImpl eventBandSongVersionService;

    @Autowired
    public EventBandSongVersionController(EventBandSongVersionServiceImpl eventBandSongVersionService) {
        this.eventBandSongVersionService = eventBandSongVersionService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EventBandSongVersionDTO> createEventBandSongVersion(@RequestBody EventBandSongVersionEditableDTO eventBandSongVersionEditableDTO) {
        return ResponseEntity.ok(eventBandSongVersionService.addEventBandSongVersion(eventBandSongVersionEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventBandSongVersionDTO> readEventBandSongVersionById(@PathVariable("id") UUID id) {
        return eventBandSongVersionService.getEventBandSongVersionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventBandSongVersionDTO>> readAllEventBandSongVersions() {
        return ResponseEntity.ok(eventBandSongVersionService.getAllEventBandSongVersions());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<EventBandSongVersionDTO> updateEventBandSongVersionById(@PathVariable("id") UUID id, @RequestBody EventBandSongVersionEditableDTO eventBandSongVersionEditableDTO) {
        return ResponseEntity.ok(eventBandSongVersionService.modifyEventBandSongVersionById(id, eventBandSongVersionEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventBandSongVersionById(@PathVariable("id") UUID id) {
        eventBandSongVersionService.removeEventBandSongVersionById(id);

        return ResponseEntity.ok().build();
    }
}
