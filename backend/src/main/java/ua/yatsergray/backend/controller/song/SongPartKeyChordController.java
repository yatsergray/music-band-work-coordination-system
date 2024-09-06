package ua.yatsergray.backend.controller.song;

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
@RequestMapping("/song-part-key-chords")
public class SongPartKeyChordController {
    private final SongPartKeyChordServiceImpl songPartKeyChordService;

    @Autowired
    public SongPartKeyChordController(SongPartKeyChordServiceImpl songPartKeyChordService) {
        this.songPartKeyChordService = songPartKeyChordService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongPartKeyChordDTO> createSongPartKeyChord(@RequestBody SongPartKeyChordEditableDTO songPartKeyChordEditableDTO) {
        return ResponseEntity.ok(songPartKeyChordService.addSongPartKeyChord(songPartKeyChordEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongPartKeyChordDTO> readSongPartKeyChordById(@PathVariable("id") UUID id) {
        return songPartKeyChordService.getSongPartKeyChordById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongPartKeyChordDTO>> readAllSongPartKeyChords() {
        return ResponseEntity.ok(songPartKeyChordService.getAllSongPartKeyChords());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<SongPartKeyChordDTO> updateSongPartKeyChordById(@PathVariable("id") UUID id, @RequestBody SongPartKeyChordEditableDTO songPartKeyChordEditableDTO) {
        return ResponseEntity.ok(songPartKeyChordService.modifySongPartKeyChordById(id, songPartKeyChordEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongPartKeyChordById(@PathVariable("id") UUID id) {
        songPartKeyChordService.removeSongPartKeyChordById(id);

        return ResponseEntity.ok().build();
    }
}
