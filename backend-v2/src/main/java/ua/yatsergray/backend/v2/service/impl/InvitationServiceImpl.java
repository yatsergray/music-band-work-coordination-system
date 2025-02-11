package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.InvitationDTO;
import ua.yatsergray.backend.v2.domain.entity.Invitation;
import ua.yatsergray.backend.v2.domain.entity.MusicBand;
import ua.yatsergray.backend.v2.domain.entity.ParticipationStatus;
import ua.yatsergray.backend.v2.domain.entity.User;
import ua.yatsergray.backend.v2.domain.request.InvitationCreateRequest;
import ua.yatsergray.backend.v2.domain.request.InvitationUpdateRequest;
import ua.yatsergray.backend.v2.domain.type.ParticipationStatusType;
import ua.yatsergray.backend.v2.exception.*;
import ua.yatsergray.backend.v2.mapper.InvitationMapper;
import ua.yatsergray.backend.v2.repository.*;
import ua.yatsergray.backend.v2.service.InvitationService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class InvitationServiceImpl implements InvitationService {
    private final InvitationMapper invitationMapper;
    private final InvitationRepository invitationRepository;
    private final MusicBandRepository musicBandRepository;
    private final ParticipationStatusRepository participationStatusRepository;
    private final UserRepository userRepository;
    private final MusicBandUserAccessRoleRepository musicBandUserAccessRoleRepository;
    private final JwtServiceImpl jwtService;

    @Autowired
    public InvitationServiceImpl(InvitationMapper invitationMapper, InvitationRepository invitationRepository, MusicBandRepository musicBandRepository, ParticipationStatusRepository participationStatusRepository, UserRepository userRepository, MusicBandUserAccessRoleRepository musicBandUserAccessRoleRepository, JwtServiceImpl jwtService) {
        this.invitationMapper = invitationMapper;
        this.invitationRepository = invitationRepository;
        this.musicBandRepository = musicBandRepository;
        this.participationStatusRepository = participationStatusRepository;
        this.userRepository = userRepository;
        this.musicBandUserAccessRoleRepository = musicBandUserAccessRoleRepository;
        this.jwtService = jwtService;
    }

    @Override
    public InvitationDTO addInvitation(InvitationCreateRequest invitationCreateRequest) throws NoSuchMusicBandException, NoSuchParticipationStatusException, InvitationConflictException, InvitationAlreadyExistsException {
        MusicBand musicBand = musicBandRepository.findById(invitationCreateRequest.getMusicBandId())
                .orElseThrow(() -> new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", invitationCreateRequest.getMusicBandId())));
        ParticipationStatus participationStatus = participationStatusRepository.findByType(ParticipationStatusType.PENDING)
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status with type=\"%s\" does not exist", ParticipationStatusType.PENDING)));

        Optional<User> optionalUser = userRepository.findByEmail(invitationCreateRequest.getEmail());

        if (optionalUser.isPresent() && musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(musicBand.getId(), optionalUser.get().getId())) {
            throw new InvitationConflictException(String.format("User with email=\"%s\" already belongs to the Music band with id=\"%s\"", invitationCreateRequest.getEmail(), invitationCreateRequest.getMusicBandId()));
        }

        if (invitationRepository.existsByMusicBandIdAndEmail(invitationCreateRequest.getMusicBandId(), invitationCreateRequest.getEmail())) {
            throw new InvitationAlreadyExistsException(String.format("Invitation with bandId=\"%s\" and email=\"%s\" already exists", invitationCreateRequest.getMusicBandId(), invitationCreateRequest.getEmail()));
        }

        Invitation invitation = Invitation.builder()
                .email(invitationCreateRequest.getEmail())
                .token(jwtService.generateInvitationToken(invitationCreateRequest.getEmail(), invitationCreateRequest.getMusicBandId()))
                .musicBand(musicBand)
                .participationStatus(participationStatus)
                .build();

        return invitationMapper.mapToInvitationDTO(invitationRepository.save(invitation));
    }

    @Override
    public Optional<InvitationDTO> getInvitationById(UUID invitationId) {
        return invitationRepository.findById(invitationId).map(invitationMapper::mapToInvitationDTO);
    }

    @Override
    public List<InvitationDTO> getAllInvitations() {
        return invitationMapper.mapAllToInvitationDTOList(invitationRepository.findAll());
    }

    @Override
    public InvitationDTO modifyInvitationById(UUID invitationId, InvitationUpdateRequest invitationUpdateRequest) throws NoSuchInvitationException, NoSuchParticipationStatusException {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchInvitationException(String.format("Invitation with id=\"%s\" does not exist", invitationId)));
        ParticipationStatus participationStatus = participationStatusRepository.findById(invitationUpdateRequest.getParticipationStatusId())
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status with id=\"%s\" does not exist", invitationUpdateRequest.getParticipationStatusId())));

        invitation.setParticipationStatus(participationStatus);

        return invitationMapper.mapToInvitationDTO(invitationRepository.save(invitation));
    }

    @Override
    public void removeInvitationById(UUID invitationId) throws NoSuchInvitationException {
        if (!invitationRepository.existsById(invitationId)) {
            throw new NoSuchInvitationException(String.format("Invitation with id=\"%s\" does not exist", invitationId));
        }

        invitationRepository.deleteById(invitationId);
    }
}
