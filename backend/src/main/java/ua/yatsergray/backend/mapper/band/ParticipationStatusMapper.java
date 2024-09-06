package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.ParticipationStatusDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ParticipationStatusEditableDTO;
import ua.yatsergray.backend.domain.entity.band.ParticipationStatus;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipationStatusMapper {

    ParticipationStatusMapper INSTANCE = Mappers.getMapper(ParticipationStatusMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventUsers", ignore = true)
    @Mapping(target = "invitations", ignore = true)
    ParticipationStatus mapToParticipationStatus(ParticipationStatusEditableDTO participationStatusEditableDTO);

    ParticipationStatusDTO mapToParticipationStatusDTO(ParticipationStatus participationStatus);

    ParticipationStatusEditableDTO mapToParticipationStatusEditableDTO(ParticipationStatus participationStatus);

    List<ParticipationStatusDTO> mapAllToParticipationStatusDTOList(List<ParticipationStatus> participationStatuses);
}
