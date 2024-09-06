package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.BandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandSongVersionEditableDTO;
import ua.yatsergray.backend.service.band.impl.BandSongVersionServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/band-song-versions")
public class BandSongVersionController {
    private final BandSongVersionServiceImpl bandSongVersionService;

    @Autowired
    public BandSongVersionController(BandSongVersionServiceImpl bandSongVersionService) {
        this.bandSongVersionService = bandSongVersionService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<BandSongVersionDTO> createBandSongVersion(@RequestBody BandSongVersionEditableDTO bandSongVersionEditableDTO) {
        return ResponseEntity.ok(bandSongVersionService.addBandSongVersion(bandSongVersionEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandSongVersionDTO> readBandSongVersionById(@PathVariable("id") UUID id) {
        return bandSongVersionService.getBandSongVersionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BandSongVersionDTO>> readAllBandSongVersions() {
        return ResponseEntity.ok(bandSongVersionService.getAllBandSongVersions());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<BandSongVersionDTO> updateBandSongVersionById(@PathVariable("id") UUID id, @RequestBody BandSongVersionEditableDTO bandSongVersionEditableDTO) {
        return ResponseEntity.ok(bandSongVersionService.modifyBandSongVersionById(id, bandSongVersionEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBandSongVersionById(@PathVariable("id") UUID id) {
        bandSongVersionService.removeBandSongVersionById(id);

        return ResponseEntity.ok().build();
    }
}
