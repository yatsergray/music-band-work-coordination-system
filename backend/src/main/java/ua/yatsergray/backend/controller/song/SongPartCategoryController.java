package ua.yatsergray.backend.controller.song;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.SongPartCategoryDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartCategoryEditableDTO;
import ua.yatsergray.backend.service.song.impl.SongPartCategoryServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/song-part-categories")
public class SongPartCategoryController {
    private final SongPartCategoryServiceImpl songPartCategoryService;

    @Autowired
    public SongPartCategoryController(SongPartCategoryServiceImpl songPartCategoryService) {
        this.songPartCategoryService = songPartCategoryService;
    }

    @PostMapping
    public ResponseEntity<SongPartCategoryDTO> createSongPartCategory(@RequestBody SongPartCategoryEditableDTO songPartCategoryEditableDTO) {
        return ResponseEntity.ok(songPartCategoryService.addSongPartCategory(songPartCategoryEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongPartCategoryDTO> readSongPartCategoryById(@PathVariable("id") UUID id) {
        return songPartCategoryService.getSongPartCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SongPartCategoryDTO>> readAllSongPartCategories() {
        return ResponseEntity.ok(songPartCategoryService.getAllSongPartCategories());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<SongPartCategoryDTO> updateSongPartCategoryById(@PathVariable("id") UUID id, @RequestBody SongPartCategoryEditableDTO songPartCategoryEditableDTO) {
        return ResponseEntity.ok(songPartCategoryService.modifySongPartCategoryById(id, songPartCategoryEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongPartCategoryById(@PathVariable("id") UUID id) {
        songPartCategoryService.removeSongPartCategoryById(id);

        return ResponseEntity.ok().build();
    }
}
