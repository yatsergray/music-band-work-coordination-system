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

    @SneakyThrows
    @PostMapping
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistEditableDTO artistEditableDTO) {
        return ResponseEntity.ok(artistService.addArtist(artistEditableDTO));
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistDTO> readArtistById(@PathVariable("artistId") UUID artistId) {
        return artistService.getArtistById(artistId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> readAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @SneakyThrows
    @PutMapping("/{artistId}")
    public ResponseEntity<ArtistDTO> updateArtistById(@PathVariable("artistId") UUID artistId, @RequestBody ArtistEditableDTO artistEditableDTO) {
        return ResponseEntity.ok(artistService.modifyArtistById(artistId, artistEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{artistId}")
    public ResponseEntity<Void> deleteArtistById(@PathVariable("artistId") UUID artistId) {
        artistService.removeArtistById(artistId);

        return ResponseEntity.ok().build();
    }
}
