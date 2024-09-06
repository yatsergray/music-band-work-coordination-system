package ua.yatsergray.backend.controller.song;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongPartDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartEditableDTO;
import ua.yatsergray.backend.service.song.impl.SongPartServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/song-parts")
public class SongPartController {
    private final SongPartServiceImpl songPartService;

    @Autowired
    public SongPartController(SongPartServiceImpl songPartService) {
        this.songPartService = songPartService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongPartDTO> createSongPart(@RequestBody SongPartEditableDTO songPartEditableDTO) {
        return ResponseEntity.ok(songPartService.addSongPart(songPartEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongPartDTO> readSongPartById(@PathVariable("id") UUID id) {
        return songPartService.getSongPartById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongPartDTO>> readAllSongParts() {
        return ResponseEntity.ok(songPartService.getAllSongParts());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<SongPartDTO> updateSongPartById(@PathVariable("id") UUID id, @RequestBody SongPartEditableDTO songPartEditableDTO) {
        return ResponseEntity.ok(songPartService.modifySongPartById(id, songPartEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongPartById(@PathVariable("id") UUID id) {
        songPartService.removeSongPartById(id);

        return ResponseEntity.ok().build();
    }
}
