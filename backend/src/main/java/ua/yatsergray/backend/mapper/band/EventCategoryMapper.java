package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.EventCategoryDTO;
import ua.yatsergray.backend.domain.entity.band.EventCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventCategoryMapper {

    EventCategoryMapper INSTANCE = Mappers.getMapper(EventCategoryMapper.class);

    EventCategoryDTO mapToEventCategoryDTO(EventCategory eventCategory);

    List<EventCategoryDTO> mapAllToEventCategoryDTOList(List<EventCategory> eventCategories);
}
