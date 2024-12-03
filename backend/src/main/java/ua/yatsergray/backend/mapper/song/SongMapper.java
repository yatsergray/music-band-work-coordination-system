package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.entity.song.Song;

import java.util.List;

@Mapper(componentModel = "spring", uses = {KeyMapper.class, ArtistMapper.class, TimeSignatureMapper.class, SongCategoryMapper.class, SongPartMapper.class, SongPartDetailsMapper.class, SongInstrumentalPartMapper.class})
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    @Mapping(source = "key", target = "keyDTO")
    @Mapping(source = "artist", target = "artistDTO")
    @Mapping(source = "timeSignature", target = "timeSignatureDTO")
    @Mapping(source = "songCategory", target = "songCategoryDTO")
    @Mapping(source = "songParts", target = "songPartDTOList")
    @Mapping(source = "songPartDetailsSet", target = "songPartDetailsDTOList")
    @Mapping(source = "songInstrumentalParts", target = "songInstrumentalPartDTOList")
    @Mapping(source = "keys", target = "keyDTOList")
    SongDTO mapToSongDTO(Song song);

    List<SongDTO> mapAllToSongDTOList(List<Song> songs);
}
