package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.EventUserDTO;
import ua.yatsergray.backend.domain.entity.band.EventUser;
import ua.yatsergray.backend.mapper.user.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, StageRoleMapper.class, ParticipationStatusMapper.class})
public interface EventUserMapper {

    EventUserMapper INSTANCE = Mappers.getMapper(EventUserMapper.class);

    @Mapping(source = "user", target = "userDTO")
    @Mapping(source = "stageRole", target = "stageRoleDTO")
    @Mapping(source = "participationStatus", target = "participationStatusDTO")
    EventUserDTO mapToEventUserDTO(EventUser eventUser);

    List<EventUserDTO> mapAllToEventUserDTOList(List<EventUser> eventUsers);
}
