package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.ParticipationStatusDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ParticipationStatusEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchParticipationStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipationStatusService {

    ParticipationStatusDTO addParticipationStatus(ParticipationStatusEditableDTO participationStatusEditableDTO);

    Optional<ParticipationStatusDTO> getParticipationStatusById(UUID id);

    List<ParticipationStatusDTO> getAllParticipationStatuses();

    ParticipationStatusDTO modifyParticipationStatusById(UUID id, ParticipationStatusEditableDTO participationStatusEditableDTO) throws NoSuchParticipationStatusException;

    void removeParticipationStatusById(UUID id) throws NoSuchParticipationStatusException;
}
