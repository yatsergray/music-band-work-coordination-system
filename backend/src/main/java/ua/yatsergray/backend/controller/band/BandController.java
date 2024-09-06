package ua.yatsergray.backend.controller.band;

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
    public ResponseEntity<BandDTO> createBand(@RequestBody BandEditableDTO bandEditableDTO) {
        return ResponseEntity.ok(bandService.addBand(bandEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandDTO> readBandById(@PathVariable("id") UUID id) {
        return bandService.getBandById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BandDTO>> readAllBands() {
        return ResponseEntity.ok(bandService.getAllBands());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<BandDTO> updateBandById(@PathVariable("id") UUID id, @RequestBody BandEditableDTO bandEditableDTO) {
        return ResponseEntity.ok(bandService.modifyBandById(id, bandEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBandById(@PathVariable("id") UUID id) {
        bandService.removeBandById(id);

        return ResponseEntity.ok().build();
    }
}
