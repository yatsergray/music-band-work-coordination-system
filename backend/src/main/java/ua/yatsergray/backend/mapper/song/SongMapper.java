package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Song;

import java.util.List;

@Mapper(componentModel = "spring", uses = {KeyMapper.class, ArtistMapper.class, TimeSignatureMapper.class, SongPartDetailsMapper.class, SongInstrumentalPartMapper.class, KeyMapper.class})
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageFileId", ignore = true)
    @Mapping(target = "audioFileId", ignore = true)
    @Mapping(target = "key", ignore = true)
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "timeSignature", ignore = true)
    @Mapping(target = "songParts", ignore = true)
    @Mapping(target = "bandSongVersions", ignore = true)
    @Mapping(target = "songPartDetailsSet", ignore = true)
    @Mapping(target = "songInstrumentalParts", ignore = true)
    @Mapping(target = "keys", ignore = true)
    Song mapToSong(SongEditableDTO songEditableDTO);

    @Mapping(source = "key", target = "keyDTO")
    @Mapping(source = "artist", target = "artistDTO")
    @Mapping(source = "timeSignature", target = "timeSignatureDTO")
    @Mapping(source = "songPartDetailsSet", target = "songPartDetailsDTOList")
    @Mapping(source = "songInstrumentalParts", target = "songInstrumentalPartDTOList")
    @Mapping(source = "keys", target = "keyDTOList")
    SongDTO mapToSongDTO(Song song);

    @Mapping(source = "key.id", target = "keyUUID")
    @Mapping(source = "artist.id", target = "artistUUID")
    @Mapping(source = "timeSignature.id", target = "timeSignatureUUID")
    SongEditableDTO mapToSongEditableDTO(Song song);

    List<SongDTO> mapAllToSongDTOList(List<Song> songs);
}
