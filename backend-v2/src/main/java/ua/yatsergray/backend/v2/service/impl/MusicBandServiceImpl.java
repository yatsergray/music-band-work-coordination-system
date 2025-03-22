package ua.yatsergray.backend.v2.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.MusicBandDTO;
import ua.yatsergray.backend.v2.domain.dto.MusicBandUserDTO;
import ua.yatsergray.backend.v2.domain.entity.*;
import ua.yatsergray.backend.v2.domain.request.MusicBandCreateUpdateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserAccessRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MusicBandUserStageRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.type.ChatAccessRoleType;
import ua.yatsergray.backend.v2.domain.type.MusicBandAccessRoleType;
import ua.yatsergray.backend.v2.exception.*;
import ua.yatsergray.backend.v2.mapper.MusicBandMapper;
import ua.yatsergray.backend.v2.mapper.MusicBandUserMapper;
import ua.yatsergray.backend.v2.repository.*;
import ua.yatsergray.backend.v2.service.JwtService;
import ua.yatsergray.backend.v2.service.MusicBandService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class MusicBandServiceImpl implements MusicBandService {

    @PersistenceContext
    private EntityManager entityManager;

    private final MusicBandMapper musicBandMapper;
    private final MusicBandRepository musicBandRepository;
    private final UserRepository userRepository;
    private final MusicBandUserAccessRoleRepository musicBandUserAccessRoleRepository;
    private final MusicBandAccessRoleRepository musicBandAccessRoleRepository;
    private final StageRoleRepository stageRoleRepository;
    private final MusicBandUserStageRoleRepository musicBandUserStageRoleRepository;
    private final ChatUserAccessRoleRepository chatUserAccessRoleRepository;
    private final InvitationRepository invitationRepository;
    private final JwtService jwtService;

    @Autowired
    public MusicBandServiceImpl(MusicBandMapper musicBandMapper, MusicBandRepository musicBandRepository, UserRepository userRepository, MusicBandUserAccessRoleRepository musicBandUserAccessRoleRepository, MusicBandAccessRoleRepository musicBandAccessRoleRepository, StageRoleRepository stageRoleRepository, MusicBandUserStageRoleRepository musicBandUserStageRoleRepository, ChatUserAccessRoleRepository chatUserAccessRoleRepository, InvitationRepository invitationRepository, JwtService jwtService) {
        this.musicBandMapper = musicBandMapper;
        this.musicBandRepository = musicBandRepository;
        this.userRepository = userRepository;
        this.musicBandUserAccessRoleRepository = musicBandUserAccessRoleRepository;
        this.musicBandAccessRoleRepository = musicBandAccessRoleRepository;
        this.stageRoleRepository = stageRoleRepository;
        this.musicBandUserStageRoleRepository = musicBandUserStageRoleRepository;
        this.chatUserAccessRoleRepository = chatUserAccessRoleRepository;
        this.invitationRepository = invitationRepository;
        this.jwtService = jwtService;
    }

    @Override
    public MusicBandDTO addMusicBand(MusicBandCreateUpdateRequest musicBandCreateUpdateRequest) throws MusicBandAlreadyExists {
        if (musicBandRepository.existsByName(musicBandCreateUpdateRequest.getName())) {
            throw new MusicBandAlreadyExists(String.format("Music band with name=\"%s\" already exists", musicBandCreateUpdateRequest.getName()));
        }

        MusicBand musicBand = MusicBand.builder()
                .name(musicBandCreateUpdateRequest.getName())
                .createdAt(LocalDateTime.now())
                .build();

        // TODO: get user from security context, add it to music band with member, admin and owner roles

        return musicBandMapper.mapToMusicBandDTO(musicBandRepository.save(musicBand));
    }

    @Override
    public Optional<MusicBandDTO> getMusicBandById(UUID musicBandId) {
        return musicBandRepository.findById(musicBandId).map(musicBandMapper::mapToMusicBandDTO);
    }

    @Override
    public Page<MusicBandDTO> getAllMusicBandsByPageAndSize(int page, int size) {
        return musicBandRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending())).map(musicBandMapper::mapToMusicBandDTO);
    }

    @Override
    public Page<MusicBandDTO> getAllMusicBandsByUserIdAndPageAndSize(UUID userId, int page, int size) {
        return musicBandRepository.findAllByUserId(userId, PageRequest.of(page, size, Sort.by("createdAt").descending())).map(musicBandMapper::mapToMusicBandDTO);
    }

    @Override
    public MusicBandDTO modifyMusicBandById(UUID musicBandId, MusicBandCreateUpdateRequest musicBandCreateUpdateRequest) throws NoSuchMusicBandException, MusicBandAlreadyExists {
        MusicBand musicBand = musicBandRepository.findById(musicBandId)
                .orElseThrow(() -> new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", musicBandId)));

        if (!musicBandCreateUpdateRequest.getName().equals(musicBand.getName()) && musicBandRepository.existsByName(musicBandCreateUpdateRequest.getName())) {
            throw new MusicBandAlreadyExists(String.format("Music band with name=\"%s\" already exists", musicBandCreateUpdateRequest.getName()));
        }

        musicBand.setName(musicBandCreateUpdateRequest.getName());

        return musicBandMapper.mapToMusicBandDTO(musicBandRepository.save(musicBand));

    }

    @Override
    public void removeMusicBandById(UUID musicBandId) throws NoSuchMusicBandException {
        if (!musicBandRepository.existsById(musicBandId)) {
            throw new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", musicBandId));
        }

        musicBandRepository.deleteById(musicBandId);
    }

    @Override
    public MusicBandUserDTO addMusicBandUser(UUID musicBandId, MusicBandUserCreateRequest musicBandUserCreateRequest) throws NoSuchMusicBandException, NoSuchUserException, NoSuchMusicBandAccessRoleException, MusicBandUserConflictException {
        MusicBand musicBand = musicBandRepository.findById(musicBandId)
                .orElseThrow(() -> new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", musicBandId)));
        User user = userRepository.findById(musicBandUserCreateRequest.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", musicBandUserCreateRequest.getUserId())));
        MusicBandAccessRole musicBandAccessRole = musicBandAccessRoleRepository.findByType(MusicBandAccessRoleType.MEMBER)
                .orElseThrow(() -> new NoSuchMusicBandAccessRoleException(String.format("Music band access role with type=\"%s\" does not exist", MusicBandAccessRoleType.MEMBER)));

        if (musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(musicBandId, musicBandUserCreateRequest.getUserId())) {
            throw new MusicBandUserConflictException(String.format("User with id=\"%s\" already belongs to the Music band with id=\"%s\"", musicBandUserCreateRequest.getUserId(), musicBandId));
        }

        MusicBandUserAccessRole musicBandUserAccessRole = MusicBandUserAccessRole.builder()
                .musicBand(musicBand)
                .user(user)
                .musicBandAccessRole(musicBandAccessRole)
                .build();

        musicBandUserAccessRoleRepository.save(musicBandUserAccessRole);

        entityManager.flush();
        entityManager.refresh(musicBand);
        entityManager.refresh(user);

        return MusicBandUserMapper.INSTANCE.mapToMusicBandUserDTO(musicBand, user);
    }

    @Override
    public MusicBandUserDTO addMusicBandUserByInvitationToken(String invitationToken) throws NoSuchInvitationException, InvalidInvitationException, NoSuchUserException, MusicBandUserConflictException, NoSuchMusicBandException, NoSuchMusicBandAccessRoleException, NoSuchParticipationStatusException {
        Invitation invitation = invitationRepository.findByToken(invitationToken)
                .orElseThrow(() -> new NoSuchInvitationException(String.format("Invitation with token=\"%s\" does not exist", invitationToken)));

        if (!jwtService.isInvitationTokenValid(invitationToken, invitation.getEmail(), invitation.getMusicBand().getId())) {
            throw new InvalidInvitationException(String.format("Invitation token=\"%s\" is invalid", invitationToken));
        }

        User user = userRepository.findByEmail(invitation.getEmail())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with email=\"%s\" does not exist", invitation.getEmail())));

        if (musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(invitation.getMusicBand().getId(), user.getId())) {
            throw new MusicBandUserConflictException(String.format("User with id=\"%s\" already belongs to the Music band with id=\"%s\"", user.getId(), invitation.getMusicBand().getId()));
        }

        MusicBand musicBand = musicBandRepository.findById(invitation.getMusicBand().getId())
                .orElseThrow(() -> new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", invitation.getMusicBand().getId())));
        MusicBandAccessRole musicBandAccessRole = musicBandAccessRoleRepository.findByType(MusicBandAccessRoleType.MEMBER)
                .orElseThrow(() -> new NoSuchMusicBandAccessRoleException(String.format("Music band access role with type=\"%s\" does not exist", MusicBandAccessRoleType.MEMBER)));

        MusicBandUserAccessRole musicBandUserAccessRole = MusicBandUserAccessRole.builder()
                .musicBand(musicBand)
                .user(user)
                .musicBandAccessRole(musicBandAccessRole)
                .build();

        musicBandUserAccessRoleRepository.save(musicBandUserAccessRole);

        entityManager.flush();
        entityManager.refresh(musicBand);
        entityManager.refresh(user);

        return MusicBandUserMapper.INSTANCE.mapToMusicBandUserDTO(musicBand, user);
    }

    @Override
    public void removeMusicBandUser(UUID musicBandId, UUID userId) throws NoSuchMusicBandException, NoSuchUserException, MusicBandUserConflictException {
        if (!musicBandRepository.existsById(musicBandId)) {
            throw new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", musicBandId));
        }

        if (!userRepository.existsById(userId)) {
            throw new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId));
        }

        if (!musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(musicBandId, userId)) {
            throw new MusicBandUserConflictException(String.format("User with id=\"%s\" does not belong to the Music band with id=\"%s\"", userId, musicBandId));
        }

        musicBandUserAccessRoleRepository.deleteByMusicBandIdAndUserId(musicBandId, userId);
        musicBandUserStageRoleRepository.deleteByMusicBandIdAndUserId(musicBandId, userId);
        chatUserAccessRoleRepository.deleteByMusicBandIdAndUserId(musicBandId, userId);
    }

    @Override
    public MusicBandUserDTO addMusicBandUserAccessRole(UUID musicBandId, UUID userId, MusicBandUserAccessRoleCreateRequest musicBandUserAccessRoleCreateRequest) throws NoSuchMusicBandException, NoSuchUserException, NoSuchMusicBandAccessRoleException, MusicBandUserConflictException {
        MusicBand musicBand = musicBandRepository.findById(musicBandId)
                .orElseThrow(() -> new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", musicBandId)));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId)));
        MusicBandAccessRole musicBandAccessRole = musicBandAccessRoleRepository.findById(musicBandUserAccessRoleCreateRequest.getMusicBandAccessRoleId())
                .orElseThrow(() -> new NoSuchMusicBandAccessRoleException(String.format("Music band access role with id=\"%s\" does not exist", musicBandUserAccessRoleCreateRequest.getMusicBandAccessRoleId())));

        if (!musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(musicBandId, userId)) {
            throw new MusicBandUserConflictException(String.format("User with id=\"%s\" does not belong to the Music band with id=\"%s\"", userId, musicBandId));
        }

        if (musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserIdAndMusicBandAccessRoleId(musicBandId, userId, musicBandUserAccessRoleCreateRequest.getMusicBandAccessRoleId())) {
            throw new MusicBandUserConflictException(String.format("Music band user access role with musicBandId=\"%s\", userId=\"%s\" and musicBandAccessRoleId=\"%s\" already exists", musicBandId, userId, musicBandUserAccessRoleCreateRequest.getMusicBandAccessRoleId()));
        }

        MusicBandUserAccessRole musicBandUserAccessRole = MusicBandUserAccessRole.builder()
                .musicBand(musicBand)
                .user(user)
                .musicBandAccessRole(musicBandAccessRole)
                .build();

        musicBandUserAccessRoleRepository.save(musicBandUserAccessRole);

        entityManager.flush();
        entityManager.refresh(musicBand);
        entityManager.refresh(user);

        return MusicBandUserMapper.INSTANCE.mapToMusicBandUserDTO(musicBand, user);
    }

    @Override
    public void removeMusicBandUserAccessRole(UUID musicBandId, UUID userId, UUID musicBandAccessRoleId) throws NoSuchMusicBandException, NoSuchUserException, NoSuchMusicBandAccessRoleException, MusicBandUserConflictException {
        if (musicBandRepository.existsById(musicBandId)) {
            throw new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", musicBandId));
        }

        if (userRepository.existsById(userId)) {
            throw new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId));
        }

        MusicBandAccessRole bandAccessRole = musicBandAccessRoleRepository.findById(musicBandAccessRoleId)
                .orElseThrow(() -> new NoSuchMusicBandAccessRoleException(String.format("Music band access role with id=\"%s\" does not exist", musicBandAccessRoleId)));

        if (!musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(musicBandId, userId)) {
            throw new MusicBandUserConflictException(String.format("User with id=\"%s\" already belongs to the Music band with id=\"%s\"", userId, musicBandId));
        }

        if (!musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserIdAndMusicBandAccessRoleId(musicBandId, userId, musicBandAccessRoleId)) {
            throw new MusicBandUserConflictException(String.format("Music band user access role with musicBandId=\"%s\", userId=\"%s\" and musicBandAccessRoleId=\"%s\" does not exist", musicBandId, userId, musicBandAccessRoleId));
        }

        if (bandAccessRole.getType().equals(MusicBandAccessRoleType.MEMBER)) {
            throw new MusicBandUserConflictException(String.format("Music band access role with type=\"%s\" cannot be removed from User with id=\"%s\" in the Music band with id=\"%s\"", ChatAccessRoleType.MEMBER, userId, musicBandId));
        }

        musicBandUserAccessRoleRepository.deleteByMusicBandIdAndUserIdAndMusicBandAccessRoleId(musicBandId, userId, musicBandAccessRoleId);
    }

    @Override
    public MusicBandUserDTO addMusicBandUserStageRole(UUID musicBandId, UUID userId, MusicBandUserStageRoleCreateRequest musicBandUserStageRoleCreateRequest) throws NoSuchMusicBandException, NoSuchUserException, NoSuchStageRoleException, MusicBandUserConflictException {
        MusicBand musicBand = musicBandRepository.findById(musicBandId)
                .orElseThrow(() -> new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", musicBandId)));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId)));
        StageRole stageRole = stageRoleRepository.findById(musicBandUserStageRoleCreateRequest.getStageRoleId())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", musicBandUserStageRoleCreateRequest.getStageRoleId())));

        if (!musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(musicBandId, userId)) {
            throw new MusicBandUserConflictException(String.format("User with id=\"%s\" does not belong to the Music band with id=\"%s\"", userId, musicBandId));
        }

        if (musicBandUserStageRoleRepository.existsByMusicBandIdAndUserIdAndStageRoleId(musicBandId, userId, musicBandUserStageRoleCreateRequest.getStageRoleId())) {
            throw new MusicBandUserConflictException(String.format("Music band user stage role with musicBandId=\"%s\", userId=\"%s\" and stageRoleId=\"%s\" already exists", musicBandId, userId, musicBandUserStageRoleCreateRequest.getStageRoleId()));
        }

        MusicBandUserStageRole musicBandUserStageRole = MusicBandUserStageRole.builder()
                .musicBand(musicBand)
                .user(user)
                .stageRole(stageRole)
                .build();

        musicBandUserStageRoleRepository.save(musicBandUserStageRole);

        entityManager.flush();
        entityManager.refresh(musicBand);
        entityManager.refresh(user);

        return MusicBandUserMapper.INSTANCE.mapToMusicBandUserDTO(musicBand, user);
    }

    @Override
    public void removeMusicBandUserStageRole(UUID musicBandId, UUID userId, UUID stageRoleId) throws NoSuchMusicBandException, NoSuchUserException, NoSuchStageRoleException, MusicBandUserConflictException {
        if (!musicBandRepository.existsById(musicBandId)) {
            throw new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", musicBandId));
        }

        if (!userRepository.existsById(userId)) {
            throw new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId));
        }

        if (!stageRoleRepository.existsById(stageRoleId)) {
            throw new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", stageRoleId));
        }

        if (!musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(musicBandId, userId)) {
            throw new MusicBandUserConflictException(String.format("User with id=\"%s\" does not belong to the Music band with id=\"%s\"", userId, musicBandId));
        }

        if (!musicBandUserStageRoleRepository.existsByMusicBandIdAndUserIdAndStageRoleId(musicBandId, userId, stageRoleId)) {
            throw new MusicBandUserConflictException(String.format("Music band user stage role with musicBandId=\"%s\", userId=\"%s\" and stageRoleId=\"%s\" does not exist", musicBandId, userId, stageRoleId));
        }

        musicBandUserStageRoleRepository.deleteByMusicBandIdAndUserIdAndStageRoleId(musicBandId, userId, stageRoleId);
    }
}
