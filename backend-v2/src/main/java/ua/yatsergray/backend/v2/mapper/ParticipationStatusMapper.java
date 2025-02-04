package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.ParticipationStatusDTO;
import ua.yatsergray.backend.v2.domain.entity.ParticipationStatus;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipationStatusMapper {

    ParticipationStatusMapper INSTANCE = Mappers.getMapper(ParticipationStatusMapper.class);

    ParticipationStatusDTO mapToParticipationStatusDTO(ParticipationStatus participationStatus);

    List<ParticipationStatusDTO> mapAllToParticipationStatusDTOList(List<ParticipationStatus> participationStatuses);
}
