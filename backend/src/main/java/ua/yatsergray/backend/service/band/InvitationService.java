package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.InvitationDTO;
import ua.yatsergray.backend.domain.request.band.InvitationCreateRequest;
import ua.yatsergray.backend.domain.request.band.InvitationUpdateRequest;
import ua.yatsergray.backend.exception.band.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvitationService {

    InvitationDTO addInvitation(InvitationCreateRequest invitationCreateRequest) throws NoSuchBandException, NoSuchParticipationStatusException, InvitationAlreadyExistsException, InvitationConflictException;

    Optional<InvitationDTO> getInvitationById(UUID invitationId);

    List<InvitationDTO> getAllInvitations();

    InvitationDTO modifyInvitationById(UUID invitationId, InvitationUpdateRequest invitationUpdateRequest) throws NoSuchInvitationException, NoSuchParticipationStatusException;

    void removeInvitationById(UUID invitationId) throws NoSuchInvitationException;
}
