package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongPartKeyChordDTO;
import ua.yatsergray.backend.domain.entity.song.SongPartKeyChord;

import java.util.List;

@Mapper(componentModel = "spring", uses = {KeyMapper.class, ChordMapper.class})
public interface SongPartKeyChordMapper {

    SongPartKeyChordMapper INSTANCE = Mappers.getMapper(SongPartKeyChordMapper.class);

    @Mapping(source = "key", target = "keyDTO")
    @Mapping(source = "chord", target = "chordDTO")
    SongPartKeyChordDTO mapToSongPartKeyChordDTO(SongPartKeyChord songPartKeyChord);

    List<SongPartKeyChordDTO> mapAllToSongPartKeyChordDTOList(List<SongPartKeyChord> songPartKeyChords);
}
