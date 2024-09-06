package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.EventUserDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventUserEditableDTO;
import ua.yatsergray.backend.domain.entity.band.EventUser;
import ua.yatsergray.backend.mapper.user.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, StageRoleMapper.class, ParticipationStatusMapper.class})
public interface EventUserMapper {

    EventUserMapper INSTANCE = Mappers.getMapper(EventUserMapper.class);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "event", ignore = true)
//    @Mapping(target = "stageRole", ignore = true)
//    @Mapping(target = "participationStatus", ignore = true)
//    NoSuchEventUserException mapToEventUser(EventUserEditableDTO eventUserEditableDTO);

    @Mapping(source = "user", target = "userDTO")
    @Mapping(source = "stageRole", target = "stageRoleDTO")
    @Mapping(source = "participationStatus", target = "participationStatusDTO")
    EventUserDTO mapToEventUserDTO(EventUser eventUser);

    @Mapping(source = "user.id", target = "userUUID")
    @Mapping(source = "event.id", target = "eventUUID")
    @Mapping(source = "stageRole.id", target = "stageRoleUUID")
    @Mapping(source = "participationStatus.id", target = "participationStatusUUID")
    EventUserEditableDTO mapToEventUserEditableDTO(EventUser eventUser);

    List<EventUserDTO> mapAllToEventUserDTOList(List<EventUser> eventUsers);
}
