package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandEditableDTO;
import ua.yatsergray.backend.service.band.impl.BandServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bands")
public class BandController {
    private final BandServiceImpl bandService;

    @Autowired
    public BandController(BandServiceImpl bandService) {
        this.bandService = bandService;
    }

    @PostMapping
    public ResponseEntity<BandDTO> createBand(@Valid @RequestBody BandEditableDTO bandEditableDTO) {
        return ResponseEntity.ok(bandService.addBand(bandEditableDTO));
    }

    @GetMapping("/{bandId}")
    public ResponseEntity<BandDTO> readBandById(@PathVariable("bandId") UUID bandId) {
        return bandService.getBandById(bandId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BandDTO>> readAllBands() {
        return ResponseEntity.ok(bandService.getAllBands());
    }

    @SneakyThrows
    @PutMapping("/{bandId}")
    public ResponseEntity<BandDTO> updateBandById(@PathVariable("bandId") UUID bandId, @Valid @RequestBody BandEditableDTO bandEditableDTO) {
        return ResponseEntity.ok(bandService.modifyBandById(bandId, bandEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{bandId}")
    public ResponseEntity<Void> deleteBandById(@PathVariable("bandId") UUID bandId) {
        bandService.removeBandById(bandId);

        return ResponseEntity.ok().build();
    }
}
