package ua.yatsergray.backend.controller.song;

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
@RequestMapping("/time-signatures")
public class TimeSignatureController {
    private final TimeSignatureServiceImpl timeSignatureService;

    @Autowired
    public TimeSignatureController(TimeSignatureServiceImpl timeSignatureService) {
        this.timeSignatureService = timeSignatureService;
    }

    @PostMapping
    public ResponseEntity<TimeSignatureDTO> createTimeSignature(@RequestBody TimeSignatureEditableDTO timeSignatureEditableDTO) {
        return ResponseEntity.ok(timeSignatureService.addTimeSignature(timeSignatureEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSignatureDTO> readTimeSignatureById(@PathVariable("id") UUID id) {
        return timeSignatureService.getTimeSignatureById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TimeSignatureDTO>> readAllTimeSignatures() {
        return ResponseEntity.ok(timeSignatureService.getAllTimeSignatures());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<TimeSignatureDTO> updateTimeSignatureById(@PathVariable("id") UUID id, @RequestBody TimeSignatureEditableDTO timeSignatureEditableDTO) {
        return ResponseEntity.ok(timeSignatureService.modifyTimeSignatureById(id, timeSignatureEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeSignatureById(@PathVariable("id") UUID id) {
        timeSignatureService.removeTimeSignatureById(id);

        return ResponseEntity.ok().build();
    }
}
