package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.ParticipationStatusDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipationStatusService {

    Optional<ParticipationStatusDTO> getParticipationStatusById(UUID participationStatusId);

    List<ParticipationStatusDTO> getAllParticipationStatuses();
}
