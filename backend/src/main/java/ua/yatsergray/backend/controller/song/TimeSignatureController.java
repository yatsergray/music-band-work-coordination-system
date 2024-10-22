package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.TimeSignatureDTO;
import ua.yatsergray.backend.domain.dto.song.editable.TimeSignatureEditableDTO;
import ua.yatsergray.backend.service.song.impl.TimeSignatureServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/time-signatures")
public class TimeSignatureController {
    private final TimeSignatureServiceImpl timeSignatureService;

    @Autowired
    public TimeSignatureController(TimeSignatureServiceImpl timeSignatureService) {
        this.timeSignatureService = timeSignatureService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<TimeSignatureDTO> createTimeSignature(@Valid @RequestBody TimeSignatureEditableDTO timeSignatureEditableDTO) {
        return ResponseEntity.ok(timeSignatureService.addTimeSignature(timeSignatureEditableDTO));
    }

    @GetMapping("/{timeSignatureId}")
    public ResponseEntity<TimeSignatureDTO> readTimeSignatureById(@PathVariable("timeSignatureId") UUID timeSignatureId) {
        return timeSignatureService.getTimeSignatureById(timeSignatureId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TimeSignatureDTO>> readAllTimeSignatures() {
        return ResponseEntity.ok(timeSignatureService.getAllTimeSignatures());
    }

    @SneakyThrows
    @PutMapping("/{timeSignatureId}")
    public ResponseEntity<TimeSignatureDTO> updateTimeSignatureById(@PathVariable("timeSignatureId") UUID timeSignatureId, @Valid @RequestBody TimeSignatureEditableDTO timeSignatureEditableDTO) {
        return ResponseEntity.ok(timeSignatureService.modifyTimeSignatureById(timeSignatureId, timeSignatureEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{timeSignatureId}")
    public ResponseEntity<Void> deleteTimeSignatureById(@PathVariable("timeSignatureId") UUID timeSignatureId) {
        timeSignatureService.removeTimeSignatureById(timeSignatureId);

        return ResponseEntity.ok().build();
    }
}
