package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.SongDTO;
import ua.yatsergray.backend.v2.domain.request.SongCreateUpdateRequest;
import ua.yatsergray.backend.v2.service.SongService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/songs")
public class SongController {
    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongDTO> createSong(@Valid @RequestBody SongCreateUpdateRequest songCreateUpdateRequest) {
        return ResponseEntity.ok(songService.addSong(songCreateUpdateRequest));
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongDTO> readSongById(@PathVariable("songId") UUID songId) {
        return songService.getSongById(songId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongDTO>> readAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @SneakyThrows
    @PutMapping("/{songId}")
    public ResponseEntity<SongDTO> updateSongById(@PathVariable("songId") UUID songId, @Valid @RequestBody SongCreateUpdateRequest songCreateUpdateRequest) {
        return ResponseEntity.ok(songService.modifySongById(songId, songCreateUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSongById(@PathVariable("songId") UUID songId) {
        songService.removeSongById(songId);

        return ResponseEntity.ok().build();
    }
}
