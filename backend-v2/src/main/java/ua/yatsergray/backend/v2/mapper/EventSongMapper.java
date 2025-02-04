package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.EventSongDTO;
import ua.yatsergray.backend.v2.domain.entity.EventSong;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SongMapper.class})
public interface EventSongMapper {

    EventSongMapper INSTANCE = Mappers.getMapper(EventSongMapper.class);

    @Mapping(source = "song", target = "songDTO")
    EventSongDTO mapToEventBandSongVersionDTO(EventSong eventSong);

    List<EventSongDTO> mapAllToEventBandSongVersionDTOList(List<EventSong> eventSongs);
}
