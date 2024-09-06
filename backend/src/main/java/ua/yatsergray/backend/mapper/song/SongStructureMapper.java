package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongStructureDTO;
import ua.yatsergray.backend.domain.entity.song.SongStructure;

@Mapper(componentModel = "spring", uses = {SongPartStructureDetailsMapper.class})
public interface SongStructureMapper {

    SongStructureMapper INSTANCE = Mappers.getMapper(SongStructureMapper.class);

    @Mapping(source = "songPartStructureDetailsSet", target = "songPartStructureDetailsDTOList")
    SongStructureDTO mapToSongStructureDTO(SongStructure songStructure);
}
