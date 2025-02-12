package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.EventUserDTO;
import ua.yatsergray.backend.v2.domain.dto.MusicBandUserDTO;
import ua.yatsergray.backend.v2.domain.dto.StageRoleDTO;
import ua.yatsergray.backend.v2.domain.entity.EventUser;
import ua.yatsergray.backend.v2.domain.entity.StageRole;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = {ParticipationStatusMapper.class})
public interface EventUserMapper {

    EventUserMapper INSTANCE = Mappers.getMapper(EventUserMapper.class);

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(target = "musicBandUserDTO", expression = "java(mapEventUserToMusicBandUserDTO(eventUser))")
    @Mapping(target = "stageRoleDTO", expression = "java(mapToStageRoleDTO(eventUser.getStageRole()))")
    @Mapping(source = "participationStatus", target = "participationStatusDTO")
    EventUserDTO mapToEventUserDTO(EventUser eventUser);

    List<EventUserDTO> mapAllToEventUserDTOList(List<EventUser> eventUsers);

    default MusicBandUserDTO mapEventUserToMusicBandUserDTO(EventUser eventUser) {
        if (Objects.isNull(eventUser.getUser())) {
            return null;
        }

        return MusicBandUserMapper.INSTANCE.mapToMusicBandUserDTO(eventUser.getEvent().getMusicBand(), eventUser.getUser());
    }

    default StageRoleDTO mapToStageRoleDTO(StageRole stageRole) {
        return StageRoleMapper.INSTANCE.mapToStageRoleDTO(stageRole);
    }
}
