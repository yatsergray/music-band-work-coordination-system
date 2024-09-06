package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.ChordDTO;
import ua.yatsergray.backend.domain.dto.song.editable.ChordEditableDTO;
import ua.yatsergray.backend.service.song.impl.ChordServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chords")
public class ChordController {
    private final ChordServiceImpl chordService;

    @Autowired
    public ChordController(ChordServiceImpl chordService) {
        this.chordService = chordService;
    }

    @PostMapping
    public ResponseEntity<ChordDTO> createChord(@RequestBody ChordEditableDTO chordEditableDTO) {
        return ResponseEntity.ok(chordService.addChord(chordEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChordDTO> readChordById(@PathVariable("id") UUID id) {
        return chordService.getChordById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ChordDTO>> readAllChords() {
        return ResponseEntity.ok(chordService.getAllChords());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<ChordDTO> updateChordById(@PathVariable("id") UUID id, @RequestBody ChordEditableDTO chordEditableDTO) {
        return ResponseEntity.ok(chordService.modifyChordById(id, chordEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChordById(@PathVariable("id") UUID id) {
        chordService.removeChordById(id);

        return ResponseEntity.ok().build();
    }
}
