package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongPartDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartEditableDTO;
import ua.yatsergray.backend.domain.entity.song.SongPart;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SongPartCategoryMapper.class, SongPartKeyChordMapper.class})
public interface SongPartMapper {

    SongPartMapper INSTANCE = Mappers.getMapper(SongPartMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "song", ignore = true)
    @Mapping(target = "songPartCategory", ignore = true)
    @Mapping(target = "songPartKeyChords", ignore = true)
    @Mapping(target = "songPartDetailsSet", ignore = true)
    SongPart mapToSongPart(SongPartEditableDTO songPartEditableDTO);

    @Mapping(source = "songPartCategory", target = "songPartCategoryDTO")
    @Mapping(source = "songPartKeyChords", target = "songPartKeyChordDTOList")
    SongPartDTO mapToSongPartDTO(SongPart songPart);

    @Mapping(source = "song.id", target = "songUUID")
    @Mapping(source = "songPartCategory.id", target = "songPartCategoryUUID")
    SongPartEditableDTO mapToSongPartEditableDTO(SongPart songPart);

    List<SongPartDTO> mapAllToSongPartDTOList(List<SongPart> songParts);
}
