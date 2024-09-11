package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.ParticipationStatusDTO;
import ua.yatsergray.backend.domain.entity.band.ParticipationStatus;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipationStatusMapper {

    ParticipationStatusMapper INSTANCE = Mappers.getMapper(ParticipationStatusMapper.class);

    ParticipationStatusDTO mapToParticipationStatusDTO(ParticipationStatus participationStatus);

    List<ParticipationStatusDTO> mapAllToParticipationStatusDTOList(List<ParticipationStatus> participationStatuses);
}
