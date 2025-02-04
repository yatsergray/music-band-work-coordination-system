package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.EventUserDTO;
import ua.yatsergray.backend.v2.domain.request.EventUserCreateRequest;
import ua.yatsergray.backend.v2.domain.request.EventUserUpdateRequest;
import ua.yatsergray.backend.v2.exception.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventUserService {

    EventUserDTO addEventUser(EventUserCreateRequest eventUserCreateRequest) throws NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserConflictException, EventUserAlreadyExistsException;

    Optional<EventUserDTO> getEventUserById(UUID eventUserId);

    List<EventUserDTO> getAllEventUsers();

    EventUserDTO modifyEventUserById(UUID eventUserId, EventUserUpdateRequest eventUserUpdateRequest) throws NoSuchEventUserException, NoSuchStageRoleException, NoSuchParticipationStatusException, EventUserConflictException, EventUserAlreadyExistsException;

    void removeEventUserById(UUID eventUserId) throws NoSuchEventUserException;
}
