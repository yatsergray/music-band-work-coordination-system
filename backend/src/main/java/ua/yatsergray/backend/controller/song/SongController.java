package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongEditableDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongKeyEditableDTO;
import ua.yatsergray.backend.service.song.impl.SongServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/songs")
public class SongController {
    private final SongServiceImpl songService;

    @Autowired
    public SongController(SongServiceImpl songService) {
        this.songService = songService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongDTO> createSong(@Valid @RequestBody SongEditableDTO songEditableDTO) {
        return ResponseEntity.ok(songService.addSong(songEditableDTO));
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
    public ResponseEntity<SongDTO> updateSongById(@PathVariable("songId") UUID songId, @Valid @RequestBody SongEditableDTO songEditableDTO) {
        return ResponseEntity.ok(songService.modifySongById(songId, songEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSongById(@PathVariable("songId") UUID songId) {
        songService.removeSongById(songId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/{songId}/keys")
    public ResponseEntity<SongDTO> createSongKey(@PathVariable("songId") UUID songId, @Valid @RequestBody SongKeyEditableDTO songKeyEditableDTO) {
        return ResponseEntity.ok(songService.addSongKey(songId, songKeyEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{songId}/keys/{keyId}")
    public ResponseEntity<Void> deleteSongKey(@PathVariable("songId") UUID songId, @PathVariable("keyId") UUID keyId) {
        songService.removeSongKey(songId, keyId);

        return ResponseEntity.ok().build();
    }
}
