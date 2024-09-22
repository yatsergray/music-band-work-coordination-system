package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.InvitationDTO;
import ua.yatsergray.backend.domain.dto.band.editable.InvitationEditableDTO;
import ua.yatsergray.backend.exception.band.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvitationService {

    InvitationDTO addInvitation(InvitationEditableDTO invitationEditableDTO) throws NoSuchBandException, NoSuchParticipationStatusException, InvitationAlreadyExistsException, InvitationConflictException;

    Optional<InvitationDTO> getInvitationById(UUID invitationId);

    List<InvitationDTO> getAllInvitations();

    InvitationDTO modifyInvitationById(UUID invitationId, InvitationEditableDTO invitationEditableDTO) throws NoSuchInvitationException, NoSuchBandException, NoSuchParticipationStatusException, InvitationAlreadyExistsException, InvitationConflictException;

    void removeInvitationById(UUID invitationId) throws NoSuchInvitationException;
}
