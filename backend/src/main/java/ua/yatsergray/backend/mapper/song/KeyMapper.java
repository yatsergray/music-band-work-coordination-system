package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.KeyDTO;
import ua.yatsergray.backend.domain.dto.song.editable.KeyEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Key;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KeyMapper {

    KeyMapper INSTANCE = Mappers.getMapper(KeyMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bandSongVersions", ignore = true)
    @Mapping(target = "songsWithOriginalKey", ignore = true)
    @Mapping(target = "songPartKeyChords", ignore = true)
    @Mapping(target = "songs", ignore = true)
    Key mapToKey(KeyEditableDTO keyEditableDTO);

    KeyDTO mapToKeyDTO(Key key);

    KeyEditableDTO mapToKeyEditableDTO(Key key);

    List<KeyDTO> mapAllToKeyDTOList(List<Key> keys);
}
