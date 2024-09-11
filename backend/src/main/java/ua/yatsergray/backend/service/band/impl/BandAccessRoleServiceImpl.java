package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.BandAccessRole;
import ua.yatsergray.backend.exception.band.NoSuchBandAccessRoleException;
import ua.yatsergray.backend.mapper.band.BandAccessRoleMapper;
import ua.yatsergray.backend.repository.band.BandAccessRoleRepository;
import ua.yatsergray.backend.service.band.BandAccessRoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BandAccessRoleServiceImpl implements BandAccessRoleService {
    private final BandAccessRoleMapper bandAccessRoleMapper;
    private final BandAccessRoleRepository bandAccessRoleRepository;

    @Autowired
    public BandAccessRoleServiceImpl(BandAccessRoleMapper bandAccessRoleMapper, BandAccessRoleRepository bandAccessRoleRepository) {
        this.bandAccessRoleMapper = bandAccessRoleMapper;
        this.bandAccessRoleRepository = bandAccessRoleRepository;
    }

    @Override
    public BandAccessRoleDTO addBandAccessRole(BandAccessRoleEditableDTO bandAccessRoleEditableDTO) {
        BandAccessRole bandAccessRole = BandAccessRole.builder()
                .name(bandAccessRoleEditableDTO.getName())
                .type(bandAccessRoleEditableDTO.getType())
                .build();

        return bandAccessRoleMapper.mapToBandAccessRoleDTO(bandAccessRoleRepository.save(bandAccessRole));
    }

    @Override
    public Optional<BandAccessRoleDTO> getBandAccessRoleById(UUID id) {
        return bandAccessRoleRepository.findById(id).map(bandAccessRoleMapper::mapToBandAccessRoleDTO);
    }

    @Override
    public List<BandAccessRoleDTO> getAllBandAccessRoles() {
        return bandAccessRoleMapper.mapAllToBandAccessRoleDTOList(bandAccessRoleRepository.findAll());
    }

    @Override
    public BandAccessRoleDTO modifyBandAccessRoleById(UUID id, BandAccessRoleEditableDTO bandAccessRoleEditableDTO) throws NoSuchBandAccessRoleException {
        return bandAccessRoleRepository.findById(id)
                .map(bandAccessRole -> {
                    bandAccessRole.setName(bandAccessRoleEditableDTO.getName());
                    bandAccessRole.setType(bandAccessRoleEditableDTO.getType());

                    return bandAccessRoleMapper.mapToBandAccessRoleDTO(bandAccessRoleRepository.save(bandAccessRole));
                })
                .orElseThrow(() -> new NoSuchBandAccessRoleException(String.format("Band access role does not exist with id=%s", id)));
    }

    @Override
    public void removeBandAccessRoleById(UUID id) throws NoSuchBandAccessRoleException {
        if (!bandAccessRoleRepository.existsById(id)) {
            throw new NoSuchBandAccessRoleException(String.format("Band access role does not exist with id=%s", id));
        }

        bandAccessRoleRepository.deleteById(id);
    }
}
