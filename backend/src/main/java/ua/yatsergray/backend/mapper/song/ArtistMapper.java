package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.ArtistDTO;
import ua.yatsergray.backend.domain.dto.song.editable.ArtistEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Artist;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    ArtistMapper INSTANCE = Mappers.getMapper(ArtistMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageFileId", ignore = true)
    @Mapping(target = "songs", ignore = true)
    Artist mapToArtist(ArtistEditableDTO artistEditableDTO);

    ArtistDTO mapToArtistDTO(Artist artist);

    ArtistEditableDTO mapToArtistEditableDTO(Artist artist);

    List<ArtistDTO> mapAllToArtistDTOList(List<Artist> artists);
}
