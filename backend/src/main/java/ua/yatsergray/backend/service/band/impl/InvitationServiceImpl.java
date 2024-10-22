package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.InvitationDTO;
import ua.yatsergray.backend.domain.dto.band.editable.InvitationEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Invitation;
import ua.yatsergray.backend.domain.entity.band.ParticipationStatus;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.exception.band.*;
import ua.yatsergray.backend.mapper.band.InvitationMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.band.BandUserAccessRoleRepository;
import ua.yatsergray.backend.repository.band.InvitationRepository;
import ua.yatsergray.backend.repository.band.ParticipationStatusRepository;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.band.InvitationService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class InvitationServiceImpl implements InvitationService {
    private final InvitationMapper invitationMapper;
    private final InvitationRepository invitationRepository;
    private final BandRepository bandRepository;
    private final ParticipationStatusRepository participationStatusRepository;
    private final UserRepository userRepository;
    private final BandUserAccessRoleRepository bandUserAccessRoleRepository;

    @Autowired
    public InvitationServiceImpl(InvitationMapper invitationMapper, InvitationRepository invitationRepository, BandRepository bandRepository, ParticipationStatusRepository participationStatusRepository, UserRepository userRepository, BandUserAccessRoleRepository bandUserAccessRoleRepository) {
        this.invitationMapper = invitationMapper;
        this.invitationRepository = invitationRepository;
        this.bandRepository = bandRepository;
        this.participationStatusRepository = participationStatusRepository;
        this.userRepository = userRepository;
        this.bandUserAccessRoleRepository = bandUserAccessRoleRepository;
    }

    @Override
    public InvitationDTO addInvitation(InvitationEditableDTO invitationEditableDTO) throws NoSuchBandException, NoSuchParticipationStatusException, InvitationAlreadyExistsException, InvitationConflictException {
        return invitationMapper.mapToInvitationDTO(invitationRepository.save(configureInvitation(new Invitation(), invitationEditableDTO)));
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
    public InvitationDTO modifyInvitationById(UUID invitationId, InvitationEditableDTO invitationEditableDTO) throws NoSuchInvitationException, NoSuchBandException, NoSuchParticipationStatusException, InvitationAlreadyExistsException, InvitationConflictException {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchInvitationException(String.format("Invitation with id=\"%s\" does not exist", invitationId)));

        return invitationMapper.mapToInvitationDTO(invitationRepository.save(configureInvitation(invitation, invitationEditableDTO)));
    }

    @Override
    public void removeInvitationById(UUID invitationId) throws NoSuchInvitationException {
        if (!invitationRepository.existsById(invitationId)) {
            throw new NoSuchInvitationException(String.format("Invitation with id=\"%s\" does not exist", invitationId));
        }

        invitationRepository.deleteById(invitationId);
    }

    private Invitation configureInvitation(Invitation invitation, InvitationEditableDTO invitationEditableDTO) throws NoSuchBandException, NoSuchParticipationStatusException, InvitationAlreadyExistsException, InvitationConflictException {
        Band band = bandRepository.findById(invitationEditableDTO.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", invitationEditableDTO.getBandId())));
        ParticipationStatus participationStatus = participationStatusRepository.findById(invitationEditableDTO.getParticipationStatusId())
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status with id=\"%s\" does not exist", invitationEditableDTO.getParticipationStatusId())));

        Optional<User> optionalUser = userRepository.findByEmail(invitationEditableDTO.getEmail());

        if (optionalUser.isPresent() && bandUserAccessRoleRepository.existsByBandIdAndUserId(band.getId(), optionalUser.get().getId())) {
            throw new InvitationConflictException(String.format("User with email=\"%s\" already belongs to the Band with id=\"%s\"", invitationEditableDTO.getEmail(), invitationEditableDTO.getBandId()));
        }

        if (Objects.isNull(invitation.getId())) {
            if (invitationRepository.existsByBandIdAndEmail(invitationEditableDTO.getBandId(), invitationEditableDTO.getEmail())) {
                throw new InvitationAlreadyExistsException(String.format("Invitation with bandId=\"%s\" and email=\"%s\" already exists", invitationEditableDTO.getBandId(), invitationEditableDTO.getEmail()));
            }
        } else {
            if ((!invitationEditableDTO.getBandId().equals(invitation.getBand().getId()) || !invitationEditableDTO.getEmail().equals(invitation.getEmail())) && invitationRepository.existsByBandIdAndEmail(invitationEditableDTO.getBandId(), invitationEditableDTO.getEmail())) {
                throw new InvitationAlreadyExistsException(String.format("Invitation with bandId=\"%s\" and email=\"%s\" already exists", invitationEditableDTO.getBandId(), invitationEditableDTO.getEmail()));
            }
        }

        invitation.setEmail(invitationEditableDTO.getEmail());
        invitation.setToken(UUID.randomUUID());
        invitation.setBand(band);
        invitation.setParticipationStatus(participationStatus);

        return invitation;
    }
}
