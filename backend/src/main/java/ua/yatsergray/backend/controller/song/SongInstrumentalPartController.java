package ua.yatsergray.backend.controller.song;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongInstrumentalPartDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongInstrumentalPartEditableDTO;
import ua.yatsergray.backend.service.song.impl.SongInstrumentalPartServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/song-instrumental-parts")
public class SongInstrumentalPartController {
    private final SongInstrumentalPartServiceImpl songInstrumentalPartService;

    @Autowired
    public SongInstrumentalPartController(SongInstrumentalPartServiceImpl songInstrumentalPartService) {
        this.songInstrumentalPartService = songInstrumentalPartService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongInstrumentalPartDTO> createSongInstrumentalPart(@RequestBody SongInstrumentalPartEditableDTO songInstrumentalPartEditableDTO) {
        return ResponseEntity.ok(songInstrumentalPartService.addSongInstrumentalPart(songInstrumentalPartEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongInstrumentalPartDTO> readSongInstrumentalPartById(@PathVariable("id") UUID id) {
        return songInstrumentalPartService.getSongInstrumentalPartById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongInstrumentalPartDTO>> readAllSongInstrumentalParts() {
        return ResponseEntity.ok(songInstrumentalPartService.getAllSongInstrumentalParts());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<SongInstrumentalPartDTO> updateSongInstrumentalPartById(@PathVariable("id") UUID id, @RequestBody SongInstrumentalPartEditableDTO songInstrumentalPartEditableDTO) {
        return ResponseEntity.ok(songInstrumentalPartService.modifySongInstrumentalPartById(id, songInstrumentalPartEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongInstrumentalPartById(@PathVariable("id") UUID id) {
        songInstrumentalPartService.removeSongInstrumentalPartById(id);

        return ResponseEntity.ok().build();
    }
}
