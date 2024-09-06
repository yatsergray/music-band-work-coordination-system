package ua.yatsergray.backend.controller.song;

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

    @PostMapping
    public ResponseEntity<KeyDTO> createKey(@RequestBody KeyEditableDTO keyEditableDTO) {
        return ResponseEntity.ok(keyService.addKey(keyEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeyDTO> readKeyById(@PathVariable("id") UUID id) {
        return keyService.getKeyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<KeyDTO>> readAllKeys() {
        return ResponseEntity.ok(keyService.getAllKeys());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<KeyDTO> updateKeyById(@PathVariable("id") UUID id, @RequestBody KeyEditableDTO keyEditableDTO) {
        return ResponseEntity.ok(keyService.modifyKeyById(id, keyEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeyById(@PathVariable("id") UUID id) {
        keyService.removeKeyById(id);

        return ResponseEntity.ok().build();
    }
}
