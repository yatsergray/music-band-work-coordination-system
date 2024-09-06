package ua.yatsergray.backend.controller.song;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.ArtistDTO;
import ua.yatsergray.backend.domain.dto.song.editable.ArtistEditableDTO;
import ua.yatsergray.backend.service.song.impl.ArtistServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistServiceImpl artistService;

    @Autowired
    public ArtistController(ArtistServiceImpl artistService) {
        this.artistService = artistService;
    }

    @PostMapping
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistEditableDTO artistEditableDTO) {
        return ResponseEntity.ok(artistService.addArtist(artistEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> readArtistById(@PathVariable("id") UUID id) {
        return artistService.getArtistById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> readAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> updateArtistById(@PathVariable("id") UUID id, @RequestBody ArtistEditableDTO artistEditableDTO) {
        return ResponseEntity.ok(artistService.modifyArtistById(id, artistEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtistById(@PathVariable("id") UUID id) {
        artistService.removeArtistById(id);

        return ResponseEntity.ok().build();
    }
}
