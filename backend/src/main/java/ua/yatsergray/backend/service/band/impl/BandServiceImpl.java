package ua.yatsergray.backend.service.band.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.dto.band.BandUserDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandUserAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandUserEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandUserStageRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.*;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.domain.type.band.BandAccessRoleType;
import ua.yatsergray.backend.domain.type.band.ChatAccessRoleType;
import ua.yatsergray.backend.exception.band.BandUserConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandAccessRoleException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.mapper.band.BandMapper;
import ua.yatsergray.backend.mapper.band.BandUserMapper;
import ua.yatsergray.backend.repository.band.*;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.band.BandService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class BandServiceImpl implements BandService {

    @PersistenceContext
    private EntityManager entityManager;

    private final BandMapper bandMapper;
    private final BandRepository bandRepository;
    private final UserRepository userRepository;
    private final BandUserAccessRoleRepository bandUserAccessRoleRepository;
    private final BandAccessRoleRepository bandAccessRoleRepository;
    private final StageRoleRepository stageRoleRepository;
    private final BandUserStageRoleRepository bandUserStageRoleRepository;
    private final ChatUserAccessRoleRepository chatUserAccessRoleRepository;

    @Autowired
    public BandServiceImpl(BandMapper bandMapper, BandRepository bandRepository, UserRepository userRepository, BandUserAccessRoleRepository bandUserAccessRoleRepository, BandAccessRoleRepository bandAccessRoleRepository, StageRoleRepository stageRoleRepository, BandUserStageRoleRepository bandUserStageRoleRepository, ChatUserAccessRoleRepository chatUserAccessRoleRepository) {
        this.bandMapper = bandMapper;
        this.bandRepository = bandRepository;
        this.userRepository = userRepository;
        this.bandUserAccessRoleRepository = bandUserAccessRoleRepository;
        this.bandAccessRoleRepository = bandAccessRoleRepository;
        this.stageRoleRepository = stageRoleRepository;
        this.bandUserStageRoleRepository = bandUserStageRoleRepository;
        this.chatUserAccessRoleRepository = chatUserAccessRoleRepository;
    }

    @Override
    public BandDTO addBand(BandEditableDTO bandEditableDTO) {
        return bandMapper.mapToBandDTO(bandRepository.save(configureBand(new Band(), bandEditableDTO)));
    }

    @Override
    public Optional<BandDTO> getBandById(UUID bandId) {
        return bandRepository.findById(bandId).map(bandMapper::mapToBandDTO);
    }

    @Override
    public List<BandDTO> getAllBands() {
        return bandMapper.mapAllToBandDTOList(bandRepository.findAll());
    }

    @Override
    public BandDTO modifyBandById(UUID bandId, BandEditableDTO bandEditableDTO) throws NoSuchBandException {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", bandId)));

        return bandMapper.mapToBandDTO(bandRepository.save(configureBand(band, bandEditableDTO)));

    }

    @Override
    public void removeBandById(UUID bandId) throws NoSuchBandException {
        if (!bandRepository.existsById(bandId)) {
            throw new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", bandId));
        }

        bandRepository.deleteById(bandId);
    }

    @Override
    public BandUserDTO addBandUser(UUID bandId, BandUserEditableDTO bandUserEditableDTO) throws NoSuchBandException, NoSuchUserException, NoSuchBandAccessRoleException, BandUserConflictException {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", bandId)));
        User user = userRepository.findById(bandUserEditableDTO.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", bandUserEditableDTO.getUserId())));
        BandAccessRole bandAccessRole = bandAccessRoleRepository.findByType(BandAccessRoleType.MEMBER)
                .orElseThrow(() -> new NoSuchBandAccessRoleException(String.format("Band access role with type=\"%s\" does not exist", BandAccessRoleType.MEMBER)));

        if (bandUserAccessRoleRepository.existsByBandIdAndUserId(bandId, bandUserEditableDTO.getUserId())) {
            throw new BandUserConflictException(String.format("User with id=%s already belongs to the Band with id=\"%s\"", bandUserEditableDTO.getUserId(), bandId));
        }

        BandUserAccessRole bandUserAccessRole = BandUserAccessRole.builder()
                .band(band)
                .user(user)
                .bandAccessRole(bandAccessRole)
                .build();

        bandUserAccessRoleRepository.save(bandUserAccessRole);

        entityManager.refresh(band);
        entityManager.refresh(user);

        return BandUserMapper.INSTANCE.mapToBandUserDTO(band, user);
    }

    @Override
    public void removeBandUser(UUID bandId, UUID userId) throws NoSuchBandException, NoSuchUserException, BandUserConflictException {
        if (!bandRepository.existsById(bandId)) {
            throw new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", bandId));
        }

        if (!userRepository.existsById(userId)) {
            throw new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId));
        }

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(bandId, userId)) {
            throw new BandUserConflictException(String.format("User with id=\"%s\" does not belong to the Band with id=\"%s\"", userId, bandId));
        }

        bandUserAccessRoleRepository.deleteByBandIdAndUserId(bandId, userId);
        bandUserStageRoleRepository.deleteByBandIdAndUserId(bandId, userId);
        chatUserAccessRoleRepository.deleteByBandIdAndUserId(bandId, userId);
    }

    @Override
    public BandUserDTO addBandUserAccessRole(UUID bandId, UUID userId, BandUserAccessRoleEditableDTO bandUserAccessRoleEditableDTO) throws NoSuchBandException, NoSuchUserException, NoSuchBandAccessRoleException, BandUserConflictException {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", bandId)));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId)));
        BandAccessRole bandAccessRole = bandAccessRoleRepository.findById(bandUserAccessRoleEditableDTO.getBandAccessRoleId())
                .orElseThrow(() -> new NoSuchBandAccessRoleException(String.format("Band access role with id=\"%s\" does not exist", bandUserAccessRoleEditableDTO.getBandAccessRoleId())));

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(bandId, userId)) {
            throw new BandUserConflictException(String.format("User with id=\"%s\" does not belong to the Band with id=\"%s\"", userId, bandId));
        }

        if (bandUserAccessRoleRepository.existsByBandIdAndUserIdAndBandAccessRoleId(bandId, userId, bandUserAccessRoleEditableDTO.getBandAccessRoleId())) {
            throw new BandUserConflictException(String.format("Band user access role with bandId=\"%s\", userId=\"%s\" and bandAccessRoleId=\"%s\" already exists", bandId, userId, bandUserAccessRoleEditableDTO.getBandAccessRoleId()));
        }

        BandUserAccessRole bandUserAccessRole = BandUserAccessRole.builder()
                .band(band)
                .user(user)
                .bandAccessRole(bandAccessRole)
                .build();

        bandUserAccessRoleRepository.save(bandUserAccessRole);

        entityManager.refresh(band);
        entityManager.refresh(user);

        return BandUserMapper.INSTANCE.mapToBandUserDTO(band, user);
    }

    @Override
    public void removeBandUserAccessRole(UUID bandId, UUID userId, UUID bandAccessRoleId) throws NoSuchBandException, NoSuchUserException, NoSuchBandAccessRoleException, BandUserConflictException {
        if (bandRepository.existsById(bandId)) {
            throw new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", bandId));
        }

        if (userRepository.existsById(userId)) {
            throw new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId));
        }

        BandAccessRole bandAccessRole = bandAccessRoleRepository.findById(bandAccessRoleId)
                .orElseThrow(() -> new NoSuchBandAccessRoleException(String.format("Band access role with id=\"%s\" does not exist", bandAccessRoleId)));

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(bandId, userId)) {
            throw new BandUserConflictException(String.format("User with id=\"%s\" already belongs to the Band with id=\"%s\"", userId, bandId));
        }

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserIdAndBandAccessRoleId(bandId, userId, bandAccessRoleId)) {
            throw new BandUserConflictException(String.format("Band user access role with bandId=\"%s\", userId=\"%s\" and bandAccessRoleId=\"%s\" does not exist", bandId, userId, bandAccessRoleId));
        }

        if (bandAccessRole.getType().equals(BandAccessRoleType.MEMBER)) {
            throw new BandUserConflictException(String.format("Band access role with type=\"%s\" cannot be removed from User with id=\"%s\" in the Band with id=\"%s\"", ChatAccessRoleType.MEMBER, userId, bandId));
        }

        bandUserAccessRoleRepository.deleteByBandIdAndUserIdAndBandAccessRoleId(bandId, userId, bandAccessRoleId);
    }

    @Override
    public BandUserDTO addBandUserStageRole(UUID bandId, UUID userId, BandUserStageRoleEditableDTO bandUserStageRoleEditableDTO) throws NoSuchBandException, NoSuchUserException, NoSuchStageRoleException, BandUserConflictException {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", bandId)));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId)));
        StageRole stageRole = stageRoleRepository.findById(bandUserStageRoleEditableDTO.getStageRoleId())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", bandUserStageRoleEditableDTO.getStageRoleId())));

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(bandId, userId)) {
            throw new BandUserConflictException(String.format("User with id=\"%s\" does not belong to the Band with id=\"%s\"", userId, bandId));
        }

        if (bandUserStageRoleRepository.existsByBandIdAndUserIdAndStageRoleId(bandId, userId, bandUserStageRoleEditableDTO.getStageRoleId())) {
            throw new BandUserConflictException(String.format("Band user stage role with bandId=\"%s\", userId=\"%s\" and stageRoleId=\"%s\" already exists", bandId, userId, bandUserStageRoleEditableDTO.getStageRoleId()));
        }

        BandUserStageRole bandUserStageRole = BandUserStageRole.builder()
                .band(band)
                .user(user)
                .stageRole(stageRole)
                .build();

        bandUserStageRoleRepository.save(bandUserStageRole);

        entityManager.refresh(band);
        entityManager.refresh(user);

        return BandUserMapper.INSTANCE.mapToBandUserDTO(band, user);
    }

    @Override
    public void removeBandUserStageRole(UUID bandId, UUID userId, UUID stageRoleId) throws NoSuchBandException, NoSuchUserException, NoSuchStageRoleException, BandUserConflictException {
        if (!bandRepository.existsById(bandId)) {
            throw new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", bandId));
        }

        if (!userRepository.existsById(userId)) {
            throw new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId));
        }

        if (!stageRoleRepository.existsById(stageRoleId)) {
            throw new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", stageRoleId));
        }

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(bandId, userId)) {
            throw new BandUserConflictException(String.format("User with id=\"%s\" does not belong to the Band with id=\"%s\"", userId, bandId));
        }

        if (!bandUserStageRoleRepository.existsByBandIdAndUserIdAndStageRoleId(bandId, userId, stageRoleId)) {
            throw new BandUserConflictException(String.format("Band user stage role with bandId=\"%s\", userId=\"%s\" and stageRoleId=\"%s\" does not exist", bandId, userId, stageRoleId));
        }

        bandUserStageRoleRepository.deleteByBandIdAndUserIdAndStageRoleId(bandId, userId, stageRoleId);
    }

    private Band configureBand(Band band, BandEditableDTO bandEditableDTO) {
        band.setName(bandEditableDTO.getName());

        return band;
    }
}
