package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.EventUserDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventUserEditableDTO;
import ua.yatsergray.backend.service.band.impl.EventUserServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event-users")
public class EventUserController {
    private final EventUserServiceImpl eventUserService;

    @Autowired
    public EventUserController(EventUserServiceImpl eventUserService) {
        this.eventUserService = eventUserService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EventUserDTO> createEventUser(@RequestBody EventUserEditableDTO eventUserEditableDTO) {
        return ResponseEntity.ok(eventUserService.addEventUser(eventUserEditableDTO));
    }

    @GetMapping("/{eventUserId}")
    public ResponseEntity<EventUserDTO> readEventUserById(@PathVariable("eventUserId") UUID eventUserId) {
        return eventUserService.getEventUserById(eventUserId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventUserDTO>> readAllEventUsers() {
        return ResponseEntity.ok(eventUserService.getAllEventUsers());
    }

    @SneakyThrows
    @PutMapping("/{eventUserId}")
    public ResponseEntity<EventUserDTO> updateEventUserById(@PathVariable("eventUserId") UUID eventUserId, @RequestBody EventUserEditableDTO eventUserEditableDTO) {
        return ResponseEntity.ok(eventUserService.modifyEventUserById(eventUserId, eventUserEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{eventUserId}")
    public ResponseEntity<Void> deleteEventUserById(@PathVariable("eventUserId") UUID eventUserId) {
        eventUserService.removeEventUserById(eventUserId);

        return ResponseEntity.ok().build();
    }
}
