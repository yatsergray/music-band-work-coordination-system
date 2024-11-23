package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.entity.band.BandAccessRole;
import ua.yatsergray.backend.domain.request.band.BandAccessRoleCreateRequest;
import ua.yatsergray.backend.domain.request.band.BandAccessRoleUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.BandAccessRoleAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandAccessRoleException;
import ua.yatsergray.backend.mapper.band.BandAccessRoleMapper;
import ua.yatsergray.backend.repository.band.BandAccessRoleRepository;
import ua.yatsergray.backend.repository.band.BandUserAccessRoleRepository;
import ua.yatsergray.backend.service.band.BandAccessRoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class BandAccessRoleServiceImpl implements BandAccessRoleService {
    private final BandAccessRoleRepository bandAccessRoleRepository;
    private final BandUserAccessRoleRepository bandUserAccessRoleRepository;

    @Autowired
    public BandAccessRoleServiceImpl(BandAccessRoleRepository bandAccessRoleRepository, BandUserAccessRoleRepository bandUserAccessRoleRepository) {
        this.bandAccessRoleRepository = bandAccessRoleRepository;
        this.bandUserAccessRoleRepository = bandUserAccessRoleRepository;
    }

    @Override
    public BandAccessRoleDTO addBandAccessRole(BandAccessRoleCreateRequest bandAccessRoleCreateRequest) throws BandAccessRoleAlreadyExistsException {
        if (bandAccessRoleRepository.existsByName(bandAccessRoleCreateRequest.getName())) {
            throw new BandAccessRoleAlreadyExistsException(String.format("Band access role with name=\"%s\" already exists", bandAccessRoleCreateRequest.getName()));
        }

        if (bandAccessRoleRepository.existsByType(bandAccessRoleCreateRequest.getType())) {
            throw new BandAccessRoleAlreadyExistsException(String.format("Band access role with type=\"%s\" already exists", bandAccessRoleCreateRequest.getType()));
        }

        BandAccessRole bandAccessRole = BandAccessRole.builder()
                .name(bandAccessRoleCreateRequest.getName())
                .type(bandAccessRoleCreateRequest.getType())
                .build();

        return BandAccessRoleMapper.INSTANCE.mapToBandAccessRoleDTO(bandAccessRoleRepository.save(bandAccessRole));
    }

    @Override
    public Optional<BandAccessRoleDTO> getBandAccessRoleById(UUID bandAccessRoleId) {
        return bandAccessRoleRepository.findById(bandAccessRoleId).map(BandAccessRoleMapper.INSTANCE::mapToBandAccessRoleDTO);
    }

    @Override
    public List<BandAccessRoleDTO> getAllBandAccessRoles() {
        return BandAccessRoleMapper.INSTANCE.mapAllToBandAccessRoleDTOList(bandAccessRoleRepository.findAll());
    }

    @Override
    public BandAccessRoleDTO modifyBandAccessRoleById(UUID bandAccessRoleId, BandAccessRoleUpdateRequest bandAccessRoleUpdateRequest) throws NoSuchBandAccessRoleException, BandAccessRoleAlreadyExistsException {
        BandAccessRole bandAccessRole = bandAccessRoleRepository.findById(bandAccessRoleId)
                .orElseThrow(() -> new NoSuchBandAccessRoleException(String.format("Band access role with id=\"%s\" does not exist", bandAccessRoleId)));

        if (!bandAccessRoleUpdateRequest.getName().equals(bandAccessRole.getName()) && bandAccessRoleRepository.existsByName(bandAccessRoleUpdateRequest.getName())) {
            throw new BandAccessRoleAlreadyExistsException(String.format("Band access role with name=\"%s\" already exists", bandAccessRoleUpdateRequest.getName()));
        }

        bandAccessRole.setName(bandAccessRoleUpdateRequest.getName());

        return BandAccessRoleMapper.INSTANCE.mapToBandAccessRoleDTO(bandAccessRoleRepository.save(bandAccessRole));
    }

    @Override
    public void removeBandAccessRoleById(UUID bandAccessRoleId) throws NoSuchBandAccessRoleException, ChildEntityExistsException {
        if (!bandAccessRoleRepository.existsById(bandAccessRoleId)) {
            throw new NoSuchBandAccessRoleException(String.format("Band access role with id=\"%s\" does not exist", bandAccessRoleId));
        }

        checkIfBandAccessRoleHasChildEntity(bandAccessRoleId);

        bandAccessRoleRepository.deleteById(bandAccessRoleId);
    }

    private void checkIfBandAccessRoleHasChildEntity(UUID bandAccessRoleId) throws ChildEntityExistsException {
        long bandAccessRoleChildEntityAmount = bandUserAccessRoleRepository.countByBandAccessRoleId(bandAccessRoleId);

        if (bandAccessRoleChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Band user access role(s) depend(s) on the Band access role with id=%s", bandAccessRoleChildEntityAmount, bandAccessRoleId));
        }
    }
}
