package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.KeyDTO;
import ua.yatsergray.backend.domain.entity.song.Key;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KeyMapper {

    KeyMapper INSTANCE = Mappers.getMapper(KeyMapper.class);

    KeyDTO mapToKeyDTO(Key key);

    List<KeyDTO> mapAllToKeyDTOList(List<Key> keys);
}
