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

    @GetMapping("/{songInstrumentalPartId}")
    public ResponseEntity<SongInstrumentalPartDTO> readSongInstrumentalPartById(@PathVariable("songInstrumentalPartId") UUID songInstrumentalPartId) {
        return songInstrumentalPartService.getSongInstrumentalPartById(songInstrumentalPartId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongInstrumentalPartDTO>> readAllSongInstrumentalParts() {
        return ResponseEntity.ok(songInstrumentalPartService.getAllSongInstrumentalParts());
    }

    @SneakyThrows
    @PutMapping("/{songInstrumentalPartId}")
    public ResponseEntity<SongInstrumentalPartDTO> updateSongInstrumentalPartById(@PathVariable("songInstrumentalPartId") UUID songInstrumentalPartId, @RequestBody SongInstrumentalPartEditableDTO songInstrumentalPartEditableDTO) {
        return ResponseEntity.ok(songInstrumentalPartService.modifySongInstrumentalPartById(songInstrumentalPartId, songInstrumentalPartEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{songInstrumentalPartId}")
    public ResponseEntity<Void> deleteSongInstrumentalPartById(@PathVariable("songInstrumentalPartId") UUID songInstrumentalPartId) {
        songInstrumentalPartService.removeSongInstrumentalPartById(songInstrumentalPartId);

        return ResponseEntity.ok().build();
    }
}
