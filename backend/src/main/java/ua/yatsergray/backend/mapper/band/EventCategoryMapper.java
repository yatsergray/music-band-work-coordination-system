package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.EventCategoryDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventCategoryEditableDTO;
import ua.yatsergray.backend.domain.entity.band.EventCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventCategoryMapper {

    EventCategoryMapper INSTANCE = Mappers.getMapper(EventCategoryMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    EventCategory mapToEventCategory(EventCategoryEditableDTO eventCategoryEditableDTO);

    EventCategoryDTO mapToEventCategoryDTO(EventCategory eventCategory);

    EventCategoryEditableDTO mapToEventCategoryEditableDTO(EventCategory eventCategory);

    List<EventCategoryDTO> mapAllToEventCategoryDTOList(List<EventCategory> eventCategories);
}
