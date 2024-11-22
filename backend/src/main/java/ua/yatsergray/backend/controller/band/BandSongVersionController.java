package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.BandSongVersionDTO;
import ua.yatsergray.backend.domain.request.band.BandSongVersionCreateRequest;
import ua.yatsergray.backend.domain.request.band.BandSongVersionUpdateRequest;
import ua.yatsergray.backend.service.band.impl.BandSongVersionServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/band-song-versions")
public class BandSongVersionController {
    private final BandSongVersionServiceImpl bandSongVersionService;

    @Autowired
    public BandSongVersionController(BandSongVersionServiceImpl bandSongVersionService) {
        this.bandSongVersionService = bandSongVersionService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<BandSongVersionDTO> createBandSongVersion(@Valid @RequestBody BandSongVersionCreateRequest bandSongVersionCreateRequest) {
        return ResponseEntity.ok(bandSongVersionService.addBandSongVersion(bandSongVersionCreateRequest));
    }

    @GetMapping("/{bandSongVersionId}")
    public ResponseEntity<BandSongVersionDTO> readBandSongVersionById(@PathVariable("bandSongVersionId") UUID bandSongVersionId) {
        return bandSongVersionService.getBandSongVersionById(bandSongVersionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BandSongVersionDTO>> readAllBandSongVersions() {
        return ResponseEntity.ok(bandSongVersionService.getAllBandSongVersions());
    }

    @SneakyThrows
    @PutMapping("/{bandSongVersionId}")
    public ResponseEntity<BandSongVersionDTO> updateBandSongVersionById(@PathVariable("bandSongVersionId") UUID bandSongVersionId, @Valid @RequestBody BandSongVersionUpdateRequest bandSongVersionUpdateRequest) {
        return ResponseEntity.ok(bandSongVersionService.modifyBandSongVersionById(bandSongVersionId, bandSongVersionUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{bandSongVersionId}")
    public ResponseEntity<Void> deleteBandSongVersionById(@PathVariable("bandSongVersionId") UUID bandSongVersionId) {
        bandSongVersionService.removeBandSongVersionById(bandSongVersionId);

        return ResponseEntity.ok().build();
    }
}
