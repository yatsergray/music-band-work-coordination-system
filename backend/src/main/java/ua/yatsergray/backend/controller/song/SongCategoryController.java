package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongCategoryDTO;
import ua.yatsergray.backend.domain.request.song.SongCategoryCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongCategoryUpdateRequest;
import ua.yatsergray.backend.service.song.impl.SongCategoryServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/song-categories")
public class SongCategoryController {
    private final SongCategoryServiceImpl songCategoryService;

    @Autowired
    public SongCategoryController(SongCategoryServiceImpl songCategoryService) {
        this.songCategoryService = songCategoryService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SongCategoryDTO> createSongCategory(@Valid @RequestBody SongCategoryCreateRequest songCategoryCreateRequest) {
        return ResponseEntity.ok(songCategoryService.addSongCategory(songCategoryCreateRequest));
    }

    @GetMapping("/{songCategoryId}")
    public ResponseEntity<SongCategoryDTO> readSongCategoryById(@PathVariable("songCategoryId") UUID songCategoryId) {
        return songCategoryService.getSongCategoryById(songCategoryId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongCategoryDTO>> readAllSongCategories() {
        return ResponseEntity.ok(songCategoryService.getAllSongCategories());
    }

    @SneakyThrows
    @PutMapping("/{songCategoryId}")
    public ResponseEntity<SongCategoryDTO> updateSongCategoryById(@PathVariable("songCategoryId") UUID songCategoryId, @Valid @RequestBody SongCategoryUpdateRequest songCategoryUpdateRequest) {
        return ResponseEntity.ok(songCategoryService.modifySongCategoryById(songCategoryId, songCategoryUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{songCategoryId}")
    public ResponseEntity<Void> deleteSongCategoryById(@PathVariable("songCategoryId") UUID songCategoryId) {
        songCategoryService.removeSongCategoryById(songCategoryId);

        return ResponseEntity.ok().build();
    }
}
