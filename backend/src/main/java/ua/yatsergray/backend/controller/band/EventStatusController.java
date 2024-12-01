package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.EventStatusDTO;
import ua.yatsergray.backend.domain.request.band.EventStatusCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventStatusUpdateRequest;
import ua.yatsergray.backend.service.band.impl.EventStatusServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event-statuses")
public class EventStatusController {
    private final EventStatusServiceImpl eventStatusService;

    @Autowired
    public EventStatusController(EventStatusServiceImpl eventStatusService) {
        this.eventStatusService = eventStatusService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EventStatusDTO> createEventStatus(@Valid @RequestBody EventStatusCreateRequest eventStatusCreateRequest) {
        return ResponseEntity.ok(eventStatusService.addEventStatus(eventStatusCreateRequest));
    }

    @GetMapping("/{eventStatusId}")
    public ResponseEntity<EventStatusDTO> readEventStatusById(@PathVariable("eventStatusId") UUID eventStatusId) {
        return eventStatusService.getEventStatusById(eventStatusId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventStatusDTO>> readAllEventStatuses() {
        return ResponseEntity.ok(eventStatusService.getAllEventStatuses());
    }

    @SneakyThrows
    @PutMapping("/{eventStatusId}")
    public ResponseEntity<EventStatusDTO> updateEventStatusById(@PathVariable("eventStatusId") UUID eventStatusId, @Valid @RequestBody EventStatusUpdateRequest eventStatusUpdateRequest) {
        return ResponseEntity.ok(eventStatusService.modifyEventStatusById(eventStatusId, eventStatusUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{eventStatusId}")
    public ResponseEntity<Void> deleteEventStatusById(@PathVariable("eventStatusId") UUID eventStatusId) {
        eventStatusService.removeEventStatusById(eventStatusId);

        return ResponseEntity.ok().build();
    }
}
