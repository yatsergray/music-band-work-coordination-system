package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.ParticipationStatusDTO;
import ua.yatsergray.backend.v2.domain.type.ParticipationStatusType;
import ua.yatsergray.backend.v2.mapper.ParticipationStatusMapper;
import ua.yatsergray.backend.v2.repository.ParticipationStatusRepository;
import ua.yatsergray.backend.v2.service.ParticipationStatusService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ParticipationStatusServiceImpl implements ParticipationStatusService {
    private final ParticipationStatusMapper participationStatusMapper;
    private final ParticipationStatusRepository participationStatusRepository;

    @Autowired
    public ParticipationStatusServiceImpl(ParticipationStatusMapper participationStatusMapper, ParticipationStatusRepository participationStatusRepository) {
        this.participationStatusMapper = participationStatusMapper;
        this.participationStatusRepository = participationStatusRepository;
    }

    @Override
    public Optional<ParticipationStatusDTO> getParticipationStatusById(UUID participationStatusId) {
        return participationStatusRepository.findById(participationStatusId).map(participationStatusMapper::mapToParticipationStatusDTO);
    }

    @Override
    public List<ParticipationStatusDTO> getAllParticipationStatuses() {
        return participationStatusMapper.mapAllToParticipationStatusDTOList(participationStatusRepository.findAll());
    }
}
