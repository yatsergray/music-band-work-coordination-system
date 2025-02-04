package ua.yatsergray.backend.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.yatsergray.backend.v2.domain.dto.MusicBandAccessRoleDTO;
import ua.yatsergray.backend.v2.service.impl.MusicBandAccessRoleServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/music-band-access-roles")
public class MusicBandAccessRoleController {
    private final MusicBandAccessRoleServiceImpl musicBandAccessRoleService;

    @Autowired
    public MusicBandAccessRoleController(MusicBandAccessRoleServiceImpl musicBandAccessRoleService) {
        this.musicBandAccessRoleService = musicBandAccessRoleService;
    }

    @GetMapping("/{musicBandAccessRoleId}")
    public ResponseEntity<MusicBandAccessRoleDTO> readMusicBandAccessRoleById(@PathVariable("musicBandAccessRoleId") UUID musicBandAccessRoleId) {
        return musicBandAccessRoleService.getMusicBandAccessRoleById(musicBandAccessRoleId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MusicBandAccessRoleDTO>> readAllMusicBandAccessRoles() {
        return ResponseEntity.ok(musicBandAccessRoleService.getAllMusicBandAccessRoles());
    }
}
