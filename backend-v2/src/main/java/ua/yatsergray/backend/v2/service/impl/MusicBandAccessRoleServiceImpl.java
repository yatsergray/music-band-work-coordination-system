package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.MusicBandAccessRoleDTO;
import ua.yatsergray.backend.v2.mapper.MusicBandAccessRoleMapper;
import ua.yatsergray.backend.v2.repository.MusicBandAccessRoleRepository;
import ua.yatsergray.backend.v2.service.MusicBandAccessRoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class MusicBandAccessRoleServiceImpl implements MusicBandAccessRoleService {
    private final MusicBandAccessRoleRepository musicBandAccessRoleRepository;

    @Autowired
    public MusicBandAccessRoleServiceImpl(MusicBandAccessRoleRepository musicBandAccessRoleRepository) {
        this.musicBandAccessRoleRepository = musicBandAccessRoleRepository;
    }

    @Override
    public Optional<MusicBandAccessRoleDTO> getMusicBandAccessRoleById(UUID bandAccessRoleId) {
        return musicBandAccessRoleRepository.findById(bandAccessRoleId).map(MusicBandAccessRoleMapper.INSTANCE::mapToMusicBandAccessRoleDTO);
    }

    @Override
    public List<MusicBandAccessRoleDTO> getAllMusicBandAccessRoles() {
        return MusicBandAccessRoleMapper.INSTANCE.mapAllToMusicBandAccessRoleDTOList(musicBandAccessRoleRepository.findAll());
    }
}
