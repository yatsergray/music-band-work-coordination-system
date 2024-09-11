package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.ChordDTO;
import ua.yatsergray.backend.domain.entity.song.Chord;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChordMapper {

    ChordMapper INSTANCE = Mappers.getMapper(ChordMapper.class);

    ChordDTO mapToChordDTO(Chord chord);

    List<ChordDTO> mapAllToChordDTOList(List<Chord> chords);
}
