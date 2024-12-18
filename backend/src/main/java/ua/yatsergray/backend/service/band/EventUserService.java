package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.EventUserDTO;
import ua.yatsergray.backend.domain.request.band.EventUserCreateRequest;
import ua.yatsergray.backend.domain.request.band.EventUserUpdateRequest;
import ua.yatsergray.backend.exception.band.*;
import ua.yatsergray.backend.exception.user.NoSuchUserException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventUserService {

    EventUserDTO addEventUser(EventUserCreateRequest eventUserCreateRequest) throws NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserAlreadyExistsException, EventUserConflictException;

    Optional<EventUserDTO> getEventUserById(UUID eventUserId);

    List<EventUserDTO> getAllEventUsers();

    EventUserDTO modifyEventUserById(UUID eventUserId, EventUserUpdateRequest eventUserUpdateRequest) throws NoSuchEventUserException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserAlreadyExistsException, EventUserConflictException;

    void removeEventUserById(UUID eventUserId) throws NoSuchEventUserException;
}
