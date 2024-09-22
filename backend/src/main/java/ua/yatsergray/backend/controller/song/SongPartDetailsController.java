package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
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
    public ResponseEntity<SongPartDetailsDTO> createSongPartDetails(@Valid @RequestBody SongPartDetailsEditableDTO songPartDetailsEditableDTO) {
        return ResponseEntity.ok(songPartDetailsService.addSongPartDetails(songPartDetailsEditableDTO));
    }

    @GetMapping("/{songPartDetailsId}")
    public ResponseEntity<SongPartDetailsDTO> readSongPartDetailsById(@PathVariable("songPartDetailsId") UUID songPartDetailsId) {
        return songPartDetailsService.getSongPartDetailsById(songPartDetailsId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongPartDetailsDTO>> readAllSongPartDetails() {
        return ResponseEntity.ok(songPartDetailsService.getAllSongPartDetails());
    }

    @SneakyThrows
    @PutMapping("/{songPartDetailsId}")
    public ResponseEntity<SongPartDetailsDTO> updateSongPartDetailsById(@PathVariable("songPartDetailsId") UUID songPartDetailsId, @Valid @RequestBody SongPartDetailsEditableDTO songPartDetailsEditableDTO) {
        return ResponseEntity.ok(songPartDetailsService.modifySongPartDetailsById(songPartDetailsId, songPartDetailsEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{songPartDetailsId}")
    public ResponseEntity<Void> deleteSongPartDetailsById(@PathVariable("songPartDetailsId") UUID songPartDetailsId) {
        songPartDetailsService.removeSongPartDetailsById(songPartDetailsId);

        return ResponseEntity.ok().build();
    }
}
