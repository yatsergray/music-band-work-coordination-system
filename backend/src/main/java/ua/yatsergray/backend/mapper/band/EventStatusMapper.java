package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.EventStatusDTO;
import ua.yatsergray.backend.domain.entity.band.EventStatus;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventStatusMapper {

    EventStatusMapper INSTANCE = Mappers.getMapper(EventStatusMapper.class);

    EventStatusDTO mapToEventStatusDTO(EventStatus eventStatus);

    List<EventStatusDTO> mapAllToEventStatusDTOList(List<EventStatus> eventStatuses);
}
