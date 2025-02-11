package ua.yatsergray.backend.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.yatsergray.backend.v2.domain.dto.ParticipationStatusDTO;
import ua.yatsergray.backend.v2.service.ParticipationStatusService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/participation-statuses")
public class ParticipationStatusController {
    private final ParticipationStatusService participationStatusService;

    @Autowired
    public ParticipationStatusController(ParticipationStatusService participationStatusService) {
        this.participationStatusService = participationStatusService;
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
}
