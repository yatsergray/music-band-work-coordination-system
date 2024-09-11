package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.EventUserDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventUserEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchEventException;
import ua.yatsergray.backend.exception.band.NoSuchEventUserException;
import ua.yatsergray.backend.exception.band.NoSuchParticipationStatusException;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventUserService {

    EventUserDTO addEventUser(EventUserEditableDTO eventUserEditableDTO) throws NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException;

    Optional<EventUserDTO> getEventUserById(UUID eventUserId);

    List<EventUserDTO> getAllEventUsers();

    EventUserDTO modifyEventUserById(UUID eventUserId, EventUserEditableDTO eventUserEditableDTO) throws NoSuchEventUserException, NoSuchUserException, NoSuchEventException, NoSuchStageRoleException, NoSuchParticipationStatusException;

    void removeEventUserById(UUID eventUserId) throws NoSuchEventUserException;
}
