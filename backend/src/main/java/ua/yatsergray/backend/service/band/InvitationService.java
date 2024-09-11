package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.InvitationDTO;
import ua.yatsergray.backend.domain.dto.band.editable.InvitationEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchInvitationException;
import ua.yatsergray.backend.exception.band.NoSuchParticipationStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvitationService {

    InvitationDTO addInvitation(InvitationEditableDTO invitationEditableDTO) throws NoSuchBandException, NoSuchParticipationStatusException;

    Optional<InvitationDTO> getInvitationById(UUID invitationId);

    List<InvitationDTO> getAllInvitations();

    InvitationDTO modifyInvitationById(UUID invitationId, InvitationEditableDTO invitationEditableDTO) throws NoSuchInvitationException, NoSuchBandException, NoSuchParticipationStatusException;

    void removeInvitationById(UUID invitationId) throws NoSuchInvitationException;
}
