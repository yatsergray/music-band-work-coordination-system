package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.InvitationDTO;
import ua.yatsergray.backend.domain.dto.band.editable.InvitationEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Invitation;
import ua.yatsergray.backend.domain.entity.band.ParticipationStatus;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchInvitationException;
import ua.yatsergray.backend.exception.band.NoSuchParticipationStatusException;
import ua.yatsergray.backend.mapper.band.InvitationMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.band.InvitationRepository;
import ua.yatsergray.backend.repository.band.ParticipationStatusRepository;
import ua.yatsergray.backend.service.band.InvitationService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {
    private final InvitationMapper invitationMapper;
    private final InvitationRepository invitationRepository;
    private final BandRepository bandRepository;
    private final ParticipationStatusRepository participationStatusRepository;

    @Autowired
    public InvitationServiceImpl(InvitationMapper invitationMapper, InvitationRepository invitationRepository, BandRepository bandRepository, ParticipationStatusRepository participationStatusRepository) {
        this.invitationMapper = invitationMapper;
        this.invitationRepository = invitationRepository;
        this.bandRepository = bandRepository;
        this.participationStatusRepository = participationStatusRepository;
    }

    @Override
    public InvitationDTO addInvitation(InvitationEditableDTO invitationEditableDTO) throws NoSuchBandException, NoSuchParticipationStatusException {
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
    public InvitationDTO modifyInvitationById(UUID invitationId, InvitationEditableDTO invitationEditableDTO) throws NoSuchInvitationException, NoSuchBandException, NoSuchParticipationStatusException {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchInvitationException(String.format("Invitation does not exist with id=%s", invitationId)));

        return invitationMapper.mapToInvitationDTO(invitationRepository.save(configureInvitation(invitation, invitationEditableDTO)));
    }

    @Override
    public void removeInvitationById(UUID invitationId) throws NoSuchInvitationException {
        if (!invitationRepository.existsById(invitationId)) {
            throw new NoSuchInvitationException(String.format("Invitation does not exist with id=%s", invitationId));
        }

        invitationRepository.deleteById(invitationId);
    }

    private Invitation configureInvitation(Invitation invitation, InvitationEditableDTO invitationEditableDTO) throws NoSuchBandException, NoSuchParticipationStatusException {
        Band band = bandRepository.findById(invitationEditableDTO.getBandUUID())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band does not exist with id=%s", invitationEditableDTO.getBandUUID())));
        ParticipationStatus participationStatus = participationStatusRepository.findById(invitationEditableDTO.getParticipationStatusUUID())
                .orElseThrow(() -> new NoSuchParticipationStatusException(String.format("Participation status does not exist with id=%s", invitationEditableDTO.getParticipationStatusUUID())));

        invitation.setEmail(invitationEditableDTO.getEmail());
        invitation.setToken(invitationEditableDTO.getToken());
        invitation.setBand(band);
        invitation.setParticipationStatus(participationStatus);

        return invitation;
    }
}
