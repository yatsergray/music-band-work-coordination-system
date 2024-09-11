package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongPartDTO;
import ua.yatsergray.backend.domain.entity.song.SongPart;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SongPartCategoryMapper.class, SongPartKeyChordMapper.class})
public interface SongPartMapper {

    SongPartMapper INSTANCE = Mappers.getMapper(SongPartMapper.class);

    @Mapping(source = "songPartCategory", target = "songPartCategoryDTO")
    @Mapping(source = "songPartKeyChords", target = "songPartKeyChordDTOList")
    SongPartDTO mapToSongPartDTO(SongPart songPart);

    List<SongPartDTO> mapAllToSongPartDTOList(List<SongPart> songParts);
}
