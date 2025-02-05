package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongMoodDTO;
import ua.yatsergray.backend.domain.request.song.SongMoodCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongMoodUpdateRequest;
import ua.yatsergray.backend.service.song.SongMoodService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/song-moods")
public class SongMoodController {
    private final SongMoodService songMoodService;

    @Autowired
    public SongMoodController(SongMoodService songMoodService) {
        this.songMoodService = songMoodService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongMoodDTO> createSongMood(@Valid @RequestBody SongMoodCreateRequest songMoodCreateRequest) {
        return ResponseEntity.ok(songMoodService.addSongMood(songMoodCreateRequest));
    }

    @GetMapping("/{songMoodId}")
    public ResponseEntity<SongMoodDTO> readSongMoodById(@PathVariable("songMoodId") UUID songMoodId) {
        return songMoodService.getSongMoodById(songMoodId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongMoodDTO>> readAllSongMoods() {
        return ResponseEntity.ok(songMoodService.getAllSongMoods());
    }

    @SneakyThrows
    @PutMapping("/{songMoodId}")
    public ResponseEntity<SongMoodDTO> updateSongMoodById(@PathVariable("songMoodId") UUID songMoodId, @Valid @RequestBody SongMoodUpdateRequest songMoodUpdateRequest) {
        return ResponseEntity.ok(songMoodService.modifySongMoodById(songMoodId, songMoodUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{songMoodId}")
    public ResponseEntity<Void> deleteSongMoodById(@PathVariable("songMoodId") UUID songMoodId) {
        songMoodService.removeSongMoodById(songMoodId);

        return ResponseEntity.ok().build();
    }
}
