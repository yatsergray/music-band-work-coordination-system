package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.SongDTO;
import ua.yatsergray.backend.v2.domain.request.SongCreateRequest;
import ua.yatsergray.backend.v2.domain.request.SongUpdateRequest;
import ua.yatsergray.backend.v2.service.SongService;

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
    public ResponseEntity<SongDTO> createSong(@Valid @RequestBody SongCreateRequest songCreateRequest) {
        return ResponseEntity.ok(songService.addSong(songCreateRequest));
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongDTO> readSongById(@PathVariable("songId") UUID songId) {
        return songService.getSongById(songId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/music-band/{musicBandId}")
    public ResponseEntity<Page<SongDTO>> readAllSongsByMusicBandIdAndPageAndSize(@PathVariable("musicBandId") UUID musicBandId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(songService.getAllSongsByMusicBandIdAndPageAndSize(musicBandId, page, size));
    }

    @SneakyThrows
    @PutMapping("/{songId}")
    public ResponseEntity<SongDTO> updateSongById(@PathVariable("songId") UUID songId, @Valid @RequestBody SongUpdateRequest songUpdateRequest) {
        return ResponseEntity.ok(songService.modifySongById(songId, songUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSongById(@PathVariable("songId") UUID songId) {
        songService.removeSongById(songId);

        return ResponseEntity.ok().build();
    }
}
