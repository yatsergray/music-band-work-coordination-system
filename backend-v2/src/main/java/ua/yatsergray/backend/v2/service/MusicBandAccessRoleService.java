package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.MusicBandAccessRoleDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MusicBandAccessRoleService {

    Optional<MusicBandAccessRoleDTO> getMusicBandAccessRoleById(UUID musicBandAccessRoleId);

    List<MusicBandAccessRoleDTO> getAllMusicBandAccessRoles();
}
