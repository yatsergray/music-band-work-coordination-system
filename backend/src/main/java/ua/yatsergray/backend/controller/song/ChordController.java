package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.ChordDTO;
import ua.yatsergray.backend.domain.request.song.ChordCreateUpdateRequest;
import ua.yatsergray.backend.service.song.impl.ChordServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chords")
public class ChordController {
    private final ChordServiceImpl chordService;

    @Autowired
    public ChordController(ChordServiceImpl chordService) {
        this.chordService = chordService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<ChordDTO> createChord(@Valid @RequestBody ChordCreateUpdateRequest chordCreateUpdateRequest) {
        return ResponseEntity.ok(chordService.addChord(chordCreateUpdateRequest));
    }

    @GetMapping("/{chordId}")
    public ResponseEntity<ChordDTO> readChordById(@PathVariable("chordId") UUID chordId) {
        return chordService.getChordById(chordId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ChordDTO>> readAllChords() {
        return ResponseEntity.ok(chordService.getAllChords());
    }

    @SneakyThrows
    @PutMapping("/{chordId}")
    public ResponseEntity<ChordDTO> updateChordById(@PathVariable("chordId") UUID chordId, @Valid @RequestBody ChordCreateUpdateRequest chordCreateUpdateRequest) {
        return ResponseEntity.ok(chordService.modifyChordById(chordId, chordCreateUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{chordId}")
    public ResponseEntity<Void> deleteChordById(@PathVariable("chordId") UUID chordId) {
        chordService.removeChordById(chordId);

        return ResponseEntity.ok().build();
    }
}
