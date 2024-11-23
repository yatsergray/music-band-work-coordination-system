package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongPartCategoryDTO;
import ua.yatsergray.backend.domain.request.song.SongPartCategoryCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongPartCategoryUpdateRequest;
import ua.yatsergray.backend.service.song.impl.SongPartCategoryServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/song-part-categories")
public class SongPartCategoryController {
    private final SongPartCategoryServiceImpl songPartCategoryService;

    @Autowired
    public SongPartCategoryController(SongPartCategoryServiceImpl songPartCategoryService) {
        this.songPartCategoryService = songPartCategoryService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongPartCategoryDTO> createSongPartCategory(@Valid @RequestBody SongPartCategoryCreateRequest songPartCategoryCreateRequest) {
        return ResponseEntity.ok(songPartCategoryService.addSongPartCategory(songPartCategoryCreateRequest));
    }

    @GetMapping("/{songPartCategoryId}")
    public ResponseEntity<SongPartCategoryDTO> readSongPartCategoryById(@PathVariable("songPartCategoryId") UUID songPartCategoryId) {
        return songPartCategoryService.getSongPartCategoryById(songPartCategoryId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongPartCategoryDTO>> readAllSongPartCategories() {
        return ResponseEntity.ok(songPartCategoryService.getAllSongPartCategories());
    }

    @SneakyThrows
    @PutMapping("/{songPartCategoryId}")
    public ResponseEntity<SongPartCategoryDTO> updateSongPartCategoryById(@PathVariable("songPartCategoryId") UUID songPartCategoryId, @Valid @RequestBody SongPartCategoryUpdateRequest songPartCategoryUpdateRequest) {
        return ResponseEntity.ok(songPartCategoryService.modifySongPartCategoryById(songPartCategoryId, songPartCategoryUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{songPartCategoryId}")
    public ResponseEntity<Void> deleteSongPartCategoryById(@PathVariable("songPartCategoryId") UUID songPartCategoryId) {
        songPartCategoryService.removeSongPartCategoryById(songPartCategoryId);

        return ResponseEntity.ok().build();
    }
}
