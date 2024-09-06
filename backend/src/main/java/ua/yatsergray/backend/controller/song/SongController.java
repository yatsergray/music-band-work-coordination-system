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

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> readSongById(@PathVariable("id") UUID id) {
        return songService.getSongById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongDTO>> readAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSongById(@PathVariable("id") UUID id, @RequestBody SongEditableDTO songEditableDTO) {
        return ResponseEntity.ok(songService.modifySongById(id, songEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongById(@PathVariable("id") UUID id) {
        songService.removeSongById(id);

        return ResponseEntity.ok().build();
    }
}
