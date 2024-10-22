package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.ParticipationStatusDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ParticipationStatusEditableDTO;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchParticipationStatusException;
import ua.yatsergray.backend.exception.band.ParticipationStatusAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipationStatusService {

    ParticipationStatusDTO addParticipationStatus(ParticipationStatusEditableDTO participationStatusEditableDTO) throws ParticipationStatusAlreadyExistsException;

    Optional<ParticipationStatusDTO> getParticipationStatusById(UUID participationStatusId);

    List<ParticipationStatusDTO> getAllParticipationStatuses();

    ParticipationStatusDTO modifyParticipationStatusById(UUID participationStatusId, ParticipationStatusEditableDTO participationStatusEditableDTO) throws NoSuchParticipationStatusException, ParticipationStatusAlreadyExistsException;

    void removeParticipationStatusById(UUID participationStatusId) throws NoSuchParticipationStatusException, ChildEntityExistsException;
}
