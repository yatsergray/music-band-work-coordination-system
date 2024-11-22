package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongInstrumentalPartDTO;
import ua.yatsergray.backend.domain.request.song.SongInstrumentalPartCreateRequest;
import ua.yatsergray.backend.service.song.impl.SongInstrumentalPartServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/song-instrumental-parts")
public class SongInstrumentalPartController {
    private final SongInstrumentalPartServiceImpl songInstrumentalPartService;

    @Autowired
    public SongInstrumentalPartController(SongInstrumentalPartServiceImpl songInstrumentalPartService) {
        this.songInstrumentalPartService = songInstrumentalPartService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongInstrumentalPartDTO> createSongInstrumentalPart(@Valid @RequestBody SongInstrumentalPartCreateRequest songInstrumentalPartCreateRequest) {
        return ResponseEntity.ok(songInstrumentalPartService.addSongInstrumentalPart(songInstrumentalPartCreateRequest));
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
    public ResponseEntity<SongInstrumentalPartDTO> updateSongInstrumentalPartById(@PathVariable("songInstrumentalPartId") UUID songInstrumentalPartId) {
        return null;
    }

    @SneakyThrows
    @DeleteMapping("/{songInstrumentalPartId}")
    public ResponseEntity<Void> deleteSongInstrumentalPartById(@PathVariable("songInstrumentalPartId") UUID songInstrumentalPartId) {
        songInstrumentalPartService.removeSongInstrumentalPartById(songInstrumentalPartId);

        return ResponseEntity.ok().build();
    }
}
