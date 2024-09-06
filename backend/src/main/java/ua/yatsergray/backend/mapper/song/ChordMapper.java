package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.ChordDTO;
import ua.yatsergray.backend.domain.dto.song.editable.ChordEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Chord;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChordMapper {

    ChordMapper INSTANCE = Mappers.getMapper(ChordMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "songPartKeyChords", ignore = true)
    Chord mapToChord(ChordEditableDTO chordEditableDTO);

    ChordDTO mapToChordDTO(Chord chord);

    ChordEditableDTO mapToChordEditableDTO(Chord chord);

    List<ChordDTO> mapAllToChordDTOList(List<Chord> chords);
}
