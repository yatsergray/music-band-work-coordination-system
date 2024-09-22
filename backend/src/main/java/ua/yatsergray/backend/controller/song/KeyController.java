package ua.yatsergray.backend.controller.song;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.song.KeyDTO;
import ua.yatsergray.backend.domain.dto.song.editable.KeyEditableDTO;
import ua.yatsergray.backend.service.song.impl.KeyServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/keys")
public class KeyController {
    private final KeyServiceImpl keyService;

    @Autowired
    public KeyController(KeyServiceImpl keyService) {
        this.keyService = keyService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<KeyDTO> createKey(@Valid @RequestBody KeyEditableDTO keyEditableDTO) {
        return ResponseEntity.ok(keyService.addKey(keyEditableDTO));
    }

    @GetMapping("/{keyId}")
    public ResponseEntity<KeyDTO> readKeyById(@PathVariable("keyId") UUID keyId) {
        return keyService.getKeyById(keyId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<KeyDTO>> readAllKeys() {
        return ResponseEntity.ok(keyService.getAllKeys());
    }

    @SneakyThrows
    @PutMapping("/{keyId}")
    public ResponseEntity<KeyDTO> updateKeyById(@PathVariable("keyId") UUID keyId, @Valid @RequestBody KeyEditableDTO keyEditableDTO) {
        return ResponseEntity.ok(keyService.modifyKeyById(keyId, keyEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{keyId}")
    public ResponseEntity<Void> deleteKeyById(@PathVariable("keyId") UUID keyId) {
        keyService.removeKeyById(keyId);

        return ResponseEntity.ok().build();
    }
}
