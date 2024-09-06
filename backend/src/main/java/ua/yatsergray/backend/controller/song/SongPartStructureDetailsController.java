package ua.yatsergray.backend.controller.song;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongPartStructureDetailsDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartStructureDetailsEditableDTO;
import ua.yatsergray.backend.service.song.impl.SongPartStructureDetailsServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/song-part-structure-details")
public class SongPartStructureDetailsController {
    private final SongPartStructureDetailsServiceImpl songPartStructureDetailsService;

    @Autowired
    public SongPartStructureDetailsController(SongPartStructureDetailsServiceImpl songPartStructureDetailsService) {
        this.songPartStructureDetailsService = songPartStructureDetailsService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongPartStructureDetailsDTO> createSongPartStructureDetails(@RequestBody SongPartStructureDetailsEditableDTO songPartStructureDetailsEditableDTO) {
        return ResponseEntity.ok(songPartStructureDetailsService.addSongPartStructureDetails(songPartStructureDetailsEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongPartStructureDetailsDTO> readSongPartStructureDetailsById(@PathVariable("id") UUID id) {
        return songPartStructureDetailsService.getSongPartStructureDetailsById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongPartStructureDetailsDTO>> readAllSongPartStructureDetails() {
        return ResponseEntity.ok(songPartStructureDetailsService.getAllSongPartStructureDetails());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<SongPartStructureDetailsDTO> updateSongPartStructureDetailsById(@PathVariable("id") UUID id, @RequestBody SongPartStructureDetailsEditableDTO songPartStructureDetailsEditableDTO) {
        return ResponseEntity.ok(songPartStructureDetailsService.modifySongPartStructureDetailsById(id, songPartStructureDetailsEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongPartStructureDetailsById(@PathVariable("id") UUID id) {
        songPartStructureDetailsService.removeSongPartStructureDetailsById(id);

        return ResponseEntity.ok().build();
    }
}
