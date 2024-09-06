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

    @GetMapping("/{id}")
    public ResponseEntity<EventUserDTO> readEventUserById(@PathVariable("id") UUID id) {
        return eventUserService.getEventUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventUserDTO>> readAllEventUsers() {
        return ResponseEntity.ok(eventUserService.getAllEventUsers());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<EventUserDTO> updateEventUserById(@PathVariable("id") UUID id, @RequestBody EventUserEditableDTO eventUserEditableDTO) {
        return ResponseEntity.ok(eventUserService.modifyEventUserById(id, eventUserEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventUserById(@PathVariable("id") UUID id) {
        eventUserService.removeEventUserById(id);

        return ResponseEntity.ok().build();
    }
}
