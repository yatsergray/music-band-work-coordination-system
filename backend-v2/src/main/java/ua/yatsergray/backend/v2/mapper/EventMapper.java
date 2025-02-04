package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.EventDTO;
import ua.yatsergray.backend.v2.domain.entity.Event;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventCategoryMapper.class, EventSongMapper.class, EventUserMapper.class})
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "eventCategory", target = "eventCategoryDTO")
    @Mapping(source = "eventSongs", target = "eventSongDTOList")
    @Mapping(source = "eventUsers", target = "eventUserDTOList")
    EventDTO mapToEventDTO(Event event);

    List<EventDTO> mapAllToEventDTOList(List<Event> events);
}
