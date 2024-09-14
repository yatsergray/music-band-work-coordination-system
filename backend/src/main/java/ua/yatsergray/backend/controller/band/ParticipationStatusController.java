package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.ParticipationStatusDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ParticipationStatusEditableDTO;
import ua.yatsergray.backend.exception.band.ParticipationStatusAlreadyExistsException;
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
    public ResponseEntity<ParticipationStatusDTO> createParticipationStatus(@RequestBody ParticipationStatusEditableDTO participationStatusEditableDTO) throws ParticipationStatusAlreadyExistsException {
        return ResponseEntity.ok(participationStatusService.addParticipationStatus(participationStatusEditableDTO));
    }

    @GetMapping("/{participationStatusId}")
    public ResponseEntity<ParticipationStatusDTO> readParticipationStatusById(@PathVariable("participationStatusId") UUID participationStatusId) {
        return participationStatusService.getParticipationStatusById(participationStatusId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ParticipationStatusDTO>> readAllParticipationStatuses() {
        return ResponseEntity.ok(participationStatusService.getAllParticipationStatuses());
    }

    @SneakyThrows
    @PutMapping("/{participationStatusId}")
    public ResponseEntity<ParticipationStatusDTO> updateParticipationStatusById(@PathVariable("participationStatusId") UUID participationStatusId, @RequestBody ParticipationStatusEditableDTO participationStatusEditableDTO) {
        return ResponseEntity.ok(participationStatusService.modifyParticipationStatusById(participationStatusId, participationStatusEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{participationStatusId}")
    public ResponseEntity<Void> deleteParticipationStatusById(@PathVariable("participationStatusId") UUID participationStatusId) {
        participationStatusService.removeParticipationStatusById(participationStatusId);

        return ResponseEntity.ok().build();
    }
}
