package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongMoodDTO;
import ua.yatsergray.backend.domain.entity.song.SongMood;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongMoodMapper {

    SongMoodMapper INSTANCE = Mappers.getMapper(SongMoodMapper.class);

    SongMoodDTO mapToSongMoodDTO(SongMood songMood);

    List<SongMoodDTO> mapAllToSongMoodDTOList(List<SongMood> songMoods);
}
