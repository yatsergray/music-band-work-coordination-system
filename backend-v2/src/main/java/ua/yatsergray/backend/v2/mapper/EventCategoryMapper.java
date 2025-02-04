package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.EventCategoryDTO;
import ua.yatsergray.backend.v2.domain.entity.EventCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventCategoryMapper {

    EventCategoryMapper INSTANCE = Mappers.getMapper(EventCategoryMapper.class);

    EventCategoryDTO mapToEventCategoryDTO(EventCategory eventCategory);

    List<EventCategoryDTO> mapAllToEventCategoryDTOList(List<EventCategory> eventCategories);
}
