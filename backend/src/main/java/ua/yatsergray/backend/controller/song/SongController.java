package ua.yatsergray.backend.controller.song;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongEditableDTO;
import ua.yatsergray.backend.service.song.impl.SongServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongServiceImpl songService;

    @Autowired
    public SongController(SongServiceImpl songService) {
        this.songService = songService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongDTO> createSong(@RequestBody SongEditableDTO songEditableDTO) {
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
    public ResponseEntity<SongDTO> updateSongById(@PathVariable("songId") UUID songId, @RequestBody SongEditableDTO songEditableDTO) {
        return ResponseEntity.ok(songService.modifySongById(songId, songEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSongById(@PathVariable("songId") UUID songId) {
        songService.removeSongById(songId);

        return ResponseEntity.ok().build();
    }
}
