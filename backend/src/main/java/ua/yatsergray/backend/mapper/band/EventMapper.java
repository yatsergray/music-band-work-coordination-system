package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.EventDTO;
import ua.yatsergray.backend.domain.entity.band.Event;
import ua.yatsergray.backend.mapper.user.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventCategoryMapper.class, UserMapper.class, EventBandSongVersionMapper.class})
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "eventCategory", target = "eventCategoryDTO")
    @Mapping(source = "eventUsers", target = "eventUserDTOList")
    @Mapping(source = "eventBandSongVersions", target = "eventBandSongVersionDTOList")
    EventDTO mapToEventDTO(Event event);

    List<EventDTO> mapAllToEventDTOList(List<Event> events);
}
