package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongPartDTO;
import ua.yatsergray.backend.domain.request.song.SongPartCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongPartUpdateRequest;
import ua.yatsergray.backend.service.song.impl.SongPartServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/song-parts")
public class SongPartController {
    private final SongPartServiceImpl songPartService;

    @Autowired
    public SongPartController(SongPartServiceImpl songPartService) {
        this.songPartService = songPartService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongPartDTO> createSongPart(@Valid @RequestBody SongPartCreateRequest songPartCreateRequest) {
        return ResponseEntity.ok(songPartService.addSongPart(songPartCreateRequest));
    }

    @GetMapping("/{songPartId}")
    public ResponseEntity<SongPartDTO> readSongPartById(@PathVariable("songPartId") UUID songPartId) {
        return songPartService.getSongPartById(songPartId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongPartDTO>> readAllSongParts() {
        return ResponseEntity.ok(songPartService.getAllSongParts());
    }

    @SneakyThrows
    @PutMapping("/{songPartId}")
    public ResponseEntity<SongPartDTO> updateSongPartById(@PathVariable("songPartId") UUID songPartId, @Valid @RequestBody SongPartUpdateRequest songPartUpdateRequest) {
        return ResponseEntity.ok(songPartService.modifySongPartById(songPartId, songPartUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{songPartId}")
    public ResponseEntity<Void> deleteSongPartById(@PathVariable("songPartId") UUID songPartId) {
        songPartService.removeSongPartById(songPartId);

        return ResponseEntity.ok().build();
    }
}
