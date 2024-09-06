package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongPartKeyChordDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartKeyChordEditableDTO;
import ua.yatsergray.backend.domain.entity.song.SongPartKeyChord;

import java.util.List;

@Mapper(componentModel = "spring", uses = {KeyMapper.class, ChordMapper.class})
public interface SongPartKeyChordMapper {

    SongPartKeyChordMapper INSTANCE = Mappers.getMapper(SongPartKeyChordMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "key", ignore = true)
    @Mapping(target = "chord", ignore = true)
    @Mapping(target = "songPart", ignore = true)
    SongPartKeyChord mapToSongPartKeyChord(SongPartKeyChordEditableDTO songPartKeyChordEditableDTO);

    @Mapping(source = "key", target = "keyDTO")
    @Mapping(source = "chord", target = "chordDTO")
    SongPartKeyChordDTO mapToSongPartKeyChordDTO(SongPartKeyChord songPartKeyChord);

    @Mapping(source = "songPart.id", target = "songPartUUID")
    @Mapping(source = "key.id", target = "keyUUID")
    @Mapping(source = "chord.id", target = "chordUUID")
    SongPartKeyChordEditableDTO mapToSongPartKeyChordEditableDTO(SongPartKeyChord songPartKeyChord);

    List<SongPartKeyChordDTO> mapAllToSongPartKeyChordDTOList(List<SongPartKeyChord> songPartKeyChords);
}
