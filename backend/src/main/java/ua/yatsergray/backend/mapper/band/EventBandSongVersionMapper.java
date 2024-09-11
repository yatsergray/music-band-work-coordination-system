package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.EventBandSongVersionDTO;
import ua.yatsergray.backend.domain.entity.band.EventBandSongVersion;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BandSongVersionMapper.class})
public interface EventBandSongVersionMapper {

    EventBandSongVersionMapper INSTANCE = Mappers.getMapper(EventBandSongVersionMapper.class);

    @Mapping(source = "bandSongVersion", target = "bandSongVersionDTO")
    EventBandSongVersionDTO mapToEventBandSongVersionDTO(EventBandSongVersion eventBandSongVersion);

    List<EventBandSongVersionDTO> mapAllToEventBandSongVersionDTOList(List<EventBandSongVersion> eventBandSongVersions);
}
