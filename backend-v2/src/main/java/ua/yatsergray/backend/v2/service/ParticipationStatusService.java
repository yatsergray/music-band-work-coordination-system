package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.ParticipationStatusDTO;
import ua.yatsergray.backend.v2.domain.type.ParticipationStatusType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipationStatusService {

    Optional<ParticipationStatusDTO> getParticipationStatusById(UUID participationStatusId);

    Optional<ParticipationStatusDTO> getParticipationStatusByType(ParticipationStatusType participationStatusType);

    List<ParticipationStatusDTO> getAllParticipationStatuses();
}
