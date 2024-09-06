package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.ParticipationStatusDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ParticipationStatusEditableDTO;
import ua.yatsergray.backend.service.band.impl.ParticipationStatusServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/participation-statuses")
public class ParticipationStatusController {
    private final ParticipationStatusServiceImpl participationStatusService;

    @Autowired
    public ParticipationStatusController(ParticipationStatusServiceImpl participationStatusService) {
        this.participationStatusService = participationStatusService;
    }

    @PostMapping
    public ResponseEntity<ParticipationStatusDTO> createParticipationStatus(@RequestBody ParticipationStatusEditableDTO participationStatusEditableDTO) {
        return ResponseEntity.ok(participationStatusService.addParticipationStatus(participationStatusEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipationStatusDTO> readParticipationStatusById(@PathVariable("id") UUID id) {
        return participationStatusService.getParticipationStatusById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ParticipationStatusDTO>> readAllParticipationStatuses() {
        return ResponseEntity.ok(participationStatusService.getAllParticipationStatuses());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<ParticipationStatusDTO> updateParticipationStatusById(@PathVariable("id") UUID id, @RequestBody ParticipationStatusEditableDTO participationStatusEditableDTO) {
        return ResponseEntity.ok(participationStatusService.modifyParticipationStatusById(id, participationStatusEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipationStatusById(@PathVariable("id") UUID id) {
        participationStatusService.removeParticipationStatusById(id);

        return ResponseEntity.ok().build();
    }
}
