package ua.yatsergray.backend.controller.song;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongPartDetailsDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartDetailsEditableDTO;
import ua.yatsergray.backend.service.song.impl.SongPartDetailsServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/song-part-details")
public class SongPartDetailsController {
    private final SongPartDetailsServiceImpl songPartDetailsService;

    @Autowired
    public SongPartDetailsController(SongPartDetailsServiceImpl songPartDetailsService) {
        this.songPartDetailsService = songPartDetailsService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongPartDetailsDTO> createSongPartDetails(@RequestBody SongPartDetailsEditableDTO songPartDetailsEditableDTO) {
        return ResponseEntity.ok(songPartDetailsService.addSongPartDetails(songPartDetailsEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongPartDetailsDTO> readSongPartDetailsById(@PathVariable("id") UUID id) {
        return songPartDetailsService.getSongPartDetailsById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongPartDetailsDTO>> readAllSongPartDetails() {
        return ResponseEntity.ok(songPartDetailsService.getAllSongPartDetails());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<SongPartDetailsDTO> updateSongPartDetailsById(@PathVariable("id") UUID id, @RequestBody SongPartDetailsEditableDTO songPartDetailsEditableDTO) {
        return ResponseEntity.ok(songPartDetailsService.modifySongPartDetailsById(id, songPartDetailsEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongPartDetailsById(@PathVariable("id") UUID id) {
        songPartDetailsService.removeSongPartDetailsById(id);

        return ResponseEntity.ok().build();
    }
}
