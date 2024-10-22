package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.BandAccessRole;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.BandAccessRoleAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandAccessRoleException;
import ua.yatsergray.backend.mapper.band.BandAccessRoleMapper;
import ua.yatsergray.backend.repository.band.BandAccessRoleRepository;
import ua.yatsergray.backend.repository.band.BandUserAccessRoleRepository;
import ua.yatsergray.backend.service.band.BandAccessRoleService;

import java.util.List;
import java.util.Objects;
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
    public BandAccessRoleDTO addBandAccessRole(BandAccessRoleEditableDTO bandAccessRoleEditableDTO) throws BandAccessRoleAlreadyExistsException {
        return BandAccessRoleMapper.INSTANCE.mapToBandAccessRoleDTO(bandAccessRoleRepository.save(configureBandAccessRole(new BandAccessRole(), bandAccessRoleEditableDTO)));
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
    public BandAccessRoleDTO modifyBandAccessRoleById(UUID bandAccessRoleId, BandAccessRoleEditableDTO bandAccessRoleEditableDTO) throws NoSuchBandAccessRoleException, BandAccessRoleAlreadyExistsException {
        BandAccessRole bandAccessRole = bandAccessRoleRepository.findById(bandAccessRoleId)
                .orElseThrow(() -> new NoSuchBandAccessRoleException(String.format("Band access role with id=\"%s\" does not exist", bandAccessRoleId)));

        return BandAccessRoleMapper.INSTANCE.mapToBandAccessRoleDTO(bandAccessRoleRepository.save(configureBandAccessRole(bandAccessRole, bandAccessRoleEditableDTO)));
    }

    @Override
    public void removeBandAccessRoleById(UUID bandAccessRoleId) throws NoSuchBandAccessRoleException, ChildEntityExistsException {
        if (!bandAccessRoleRepository.existsById(bandAccessRoleId)) {
            throw new NoSuchBandAccessRoleException(String.format("Band access role with id=\"%s\" does not exist", bandAccessRoleId));
        }

        checkIfBandAccessRoleHasChildEntity(bandAccessRoleId);

        bandAccessRoleRepository.deleteById(bandAccessRoleId);
    }

    private BandAccessRole configureBandAccessRole(BandAccessRole bandAccessRole, BandAccessRoleEditableDTO bandAccessRoleEditableDTO) throws BandAccessRoleAlreadyExistsException {
        if (Objects.isNull(bandAccessRole.getId())) {
            if (bandAccessRoleRepository.existsByName(bandAccessRoleEditableDTO.getName())) {
                throw new BandAccessRoleAlreadyExistsException(String.format("Band access role with name=\"%s\" already exists", bandAccessRoleEditableDTO.getName()));
            }

            if (bandAccessRoleRepository.existsByType(bandAccessRoleEditableDTO.getType())) {
                throw new BandAccessRoleAlreadyExistsException(String.format("Band access role with type=\"%s\" already exists", bandAccessRoleEditableDTO.getType()));
            }
        } else {
            if (!bandAccessRoleEditableDTO.getName().equals(bandAccessRole.getName()) && bandAccessRoleRepository.existsByName(bandAccessRoleEditableDTO.getName())) {
                throw new BandAccessRoleAlreadyExistsException(String.format("Band access role with name=\"%s\" already exists", bandAccessRoleEditableDTO.getName()));
            }

            if (!bandAccessRoleEditableDTO.getType().equals(bandAccessRole.getType()) && bandAccessRoleRepository.existsByType(bandAccessRoleEditableDTO.getType())) {
                throw new BandAccessRoleAlreadyExistsException(String.format("Band access role with type=\"%s\" already exists", bandAccessRoleEditableDTO.getType()));
            }
        }

        bandAccessRole.setName(bandAccessRoleEditableDTO.getName());
        bandAccessRole.setType(bandAccessRoleEditableDTO.getType());

        return bandAccessRole;
    }

    private void checkIfBandAccessRoleHasChildEntity(UUID bandAccessRoleId) throws ChildEntityExistsException {
        long bandAccessRoleChildEntityAmount = bandUserAccessRoleRepository.countByBandAccessRoleId(bandAccessRoleId);

        if (bandAccessRoleChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Band user access role(s) depend(s) on the Band access role with id=%s", bandAccessRoleChildEntityAmount, bandAccessRoleId));
        }
    }
}
