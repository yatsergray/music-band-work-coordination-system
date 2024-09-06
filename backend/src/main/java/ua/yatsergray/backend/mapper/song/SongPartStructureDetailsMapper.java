package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongPartStructureDetailsDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartStructureDetailsEditableDTO;
import ua.yatsergray.backend.domain.entity.song.SongPartStructureDetails;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SongPartMapper.class})
public interface SongPartStructureDetailsMapper {

    SongPartStructureDetailsMapper INSTANCE = Mappers.getMapper(SongPartStructureDetailsMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "songPart", ignore = true)
    @Mapping(target = "songStructure", ignore = true)
    SongPartStructureDetails mapToSongPartStructureDetails(SongPartStructureDetailsEditableDTO songPartStructureDetailsEditableDTO);

    @Mapping(source = "songPart", target = "songPartDTO")
    SongPartStructureDetailsDTO mapToSongPartStructureDetailsDTO(SongPartStructureDetails songPartStructureDetails);

    @Mapping(source = "songPart.id", target = "songPartUUID")
    @Mapping(source = "songStructure.id", target = "songStructureUUID")
    SongPartStructureDetailsEditableDTO mapToSongPartStructureDetailsEditableDTO(SongPartStructureDetails songPartStructureDetails);

    List<SongPartStructureDetailsDTO> mapAllToSongPartStructureDetailsDTOList(List<SongPartStructureDetails> songPartStructureDetailsList);
}
