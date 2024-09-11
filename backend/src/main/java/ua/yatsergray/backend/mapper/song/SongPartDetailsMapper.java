package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongPartDetailsDTO;
import ua.yatsergray.backend.domain.entity.song.SongPartDetails;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SongPartMapper.class})
public interface SongPartDetailsMapper {

    SongPartDetailsMapper INSTANCE = Mappers.getMapper(SongPartDetailsMapper.class);

    @Mapping(source = "songPart", target = "songPartDTO")
    SongPartDetailsDTO mapToSongPartDetailsDTO(SongPartDetails songPartDetails);

    List<SongPartDetailsDTO> mapAllToSongPartDetailsDTOList(List<SongPartDetails> songPartDetailsList);
}
