package ua.yatsergray.backend.v2.service;

import org.springframework.data.domain.Page;
import ua.yatsergray.backend.v2.domain.dto.InvitationDTO;
import ua.yatsergray.backend.v2.domain.request.InvitationCreateRequest;
import ua.yatsergray.backend.v2.domain.request.InvitationUpdateRequest;
import ua.yatsergray.backend.v2.domain.type.ParticipationStatusType;
import ua.yatsergray.backend.v2.exception.*;

import java.util.Optional;
import java.util.UUID;

public interface InvitationService {

    InvitationDTO addInvitation(InvitationCreateRequest invitationCreateRequest) throws NoSuchMusicBandException, NoSuchParticipationStatusException, InvitationConflictException, InvitationAlreadyExistsException;

    Optional<InvitationDTO> getInvitationById(UUID invitationId);

//    List<InvitationDTO> getAllInvitations();

    Page<InvitationDTO> getAllInvitationsByPageAndSize(int page, int size);

    InvitationDTO modifyInvitationById(UUID invitationId, InvitationUpdateRequest invitationUpdateRequest) throws NoSuchInvitationException, NoSuchParticipationStatusException;

    InvitationDTO changeInvitationParticipationStatusByInvitationToken(String invitationToken, ParticipationStatusType participationStatusType) throws NoSuchInvitationException, NoSuchParticipationStatusException;

    void removeInvitationById(UUID invitationId) throws NoSuchInvitationException;
}
