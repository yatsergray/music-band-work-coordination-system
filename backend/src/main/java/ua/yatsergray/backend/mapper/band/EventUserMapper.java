package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.BandUserDTO;
import ua.yatsergray.backend.domain.dto.band.EventUserDTO;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.entity.band.EventUser;
import ua.yatsergray.backend.domain.entity.band.StageRole;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = {ParticipationStatusMapper.class})
public interface EventUserMapper {

    EventUserMapper INSTANCE = Mappers.getMapper(EventUserMapper.class);

    @Mapping(target = "bandUserDTO", expression = "java(mapEventUserToBandUserDTO(eventUser))")
    @Mapping(target = "stageRoleDTO", expression = "java(mapToStageRoleDTO(eventUser.getStageRole()))")
    @Mapping(source = "participationStatus", target = "participationStatusDTO")
    EventUserDTO mapToEventUserDTO(EventUser eventUser);

    List<EventUserDTO> mapAllToEventUserDTOList(List<EventUser> eventUsers);

    default BandUserDTO mapEventUserToBandUserDTO(EventUser eventUser) {
        if (Objects.isNull(eventUser.getUser())) {
            return null;
        }

        return BandUserMapper.INSTANCE.mapToBandUserDTO(eventUser.getEvent().getBand(), eventUser.getUser());
    }

    default StageRoleDTO mapToStageRoleDTO(StageRole stageRole) {
        return StageRoleMapper.INSTANCE.mapToStageRoleDTO(stageRole);
    }
}
