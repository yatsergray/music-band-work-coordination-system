package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.SongDTO;
import ua.yatsergray.backend.v2.domain.entity.Song;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    SongDTO mapToSongDTO(Song song);

    List<SongDTO> mapAllToSongDTOList(List<Song> songs);
}
