package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongPartKeyChordDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartKeyChordEditableDTO;
import ua.yatsergray.backend.service.song.impl.SongPartKeyChordServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/song-part-key-chords")
public class SongPartKeyChordController {
    private final SongPartKeyChordServiceImpl songPartKeyChordService;

    @Autowired
    public SongPartKeyChordController(SongPartKeyChordServiceImpl songPartKeyChordService) {
        this.songPartKeyChordService = songPartKeyChordService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongPartKeyChordDTO> createSongPartKeyChord(@Valid @RequestBody SongPartKeyChordEditableDTO songPartKeyChordEditableDTO) {
        return ResponseEntity.ok(songPartKeyChordService.addSongPartKeyChord(songPartKeyChordEditableDTO));
    }

    @GetMapping("/{songPartKeyChordId}")
    public ResponseEntity<SongPartKeyChordDTO> readSongPartKeyChordById(@PathVariable("songPartKeyChordId") UUID songPartKeyChordId) {
        return songPartKeyChordService.getSongPartKeyChordById(songPartKeyChordId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongPartKeyChordDTO>> readAllSongPartKeyChords() {
        return ResponseEntity.ok(songPartKeyChordService.getAllSongPartKeyChords());
    }

    @SneakyThrows
    @PutMapping("/{songPartKeyChordId}")
    public ResponseEntity<SongPartKeyChordDTO> updateSongPartKeyChordById(@PathVariable("songPartKeyChordId") UUID songPartKeyChordId, @Valid @RequestBody SongPartKeyChordEditableDTO songPartKeyChordEditableDTO) {
        return ResponseEntity.ok(songPartKeyChordService.modifySongPartKeyChordById(songPartKeyChordId, songPartKeyChordEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{songPartKeyChordId}")
    public ResponseEntity<Void> deleteSongPartKeyChordById(@PathVariable("songPartKeyChordId") UUID songPartKeyChordId) {
        songPartKeyChordService.removeSongPartKeyChordById(songPartKeyChordId);

        return ResponseEntity.ok().build();
    }
}
