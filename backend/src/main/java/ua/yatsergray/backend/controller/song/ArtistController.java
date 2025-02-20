package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.ArtistDTO;
import ua.yatsergray.backend.domain.request.song.ArtistCreateRequest;
import ua.yatsergray.backend.domain.request.song.ArtistUpdateRequest;
import ua.yatsergray.backend.service.song.impl.ArtistServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/artists")
public class ArtistController {
    private final ArtistServiceImpl artistService;

    @Autowired
    public ArtistController(ArtistServiceImpl artistService) {
        this.artistService = artistService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<ArtistDTO> createArtist(@Valid @RequestBody ArtistCreateRequest artistCreateRequest) {
        return ResponseEntity.ok(artistService.addArtist(artistCreateRequest));
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
    public ResponseEntity<ArtistDTO> updateArtistById(@PathVariable("artistId") UUID artistId, @Valid @RequestBody ArtistUpdateRequest artistUpdateRequest) {
        return ResponseEntity.ok(artistService.modifyArtistById(artistId, artistUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{artistId}")
    public ResponseEntity<Void> deleteArtistById(@PathVariable("artistId") UUID artistId) {
        artistService.removeArtistById(artistId);

        return ResponseEntity.ok().build();
    }
}
