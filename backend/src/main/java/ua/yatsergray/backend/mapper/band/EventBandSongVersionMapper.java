package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.EventBandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventBandSongVersionEditableDTO;
import ua.yatsergray.backend.domain.entity.band.EventBandSongVersion;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BandSongVersionMapper.class})
public interface EventBandSongVersionMapper {

    EventBandSongVersionMapper INSTANCE = Mappers.getMapper(EventBandSongVersionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "bandSongVersion", ignore = true)
    EventBandSongVersion mapToEventBandSongVersion(EventBandSongVersionEditableDTO eventBandSongVersionEditableDTO);

    @Mapping(source = "bandSongVersion", target = "bandSongVersionDTO")
    EventBandSongVersionDTO mapToEventBandSongVersionDTO(EventBandSongVersion eventBandSongVersion);

    @Mapping(source = "event.id", target = "eventUUID")
    @Mapping(source = "bandSongVersion.id", target = "bandSongVersionUUID")
    EventBandSongVersionEditableDTO mapToEventBandSongVersionEditableDTO(EventBandSongVersion eventBandSongVersion);

    List<EventBandSongVersionDTO> mapAllToEventBandSongVersionDTOList(List<EventBandSongVersion> eventBandSongVersions);
}
