package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.dto.song.SongInstrumentalPartDTO;
import ua.yatsergray.backend.domain.entity.band.StageRole;
import ua.yatsergray.backend.domain.entity.song.SongInstrumentalPart;
import ua.yatsergray.backend.mapper.band.StageRoleMapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongInstrumentalPartMapper {

    SongInstrumentalPartMapper INSTANCE = Mappers.getMapper(SongInstrumentalPartMapper.class);

    @Mapping(target = "stageRoleDTO", expression = "java(mapToStageRoleDTO(songInstrumentalPart.getStageRole()))")
    SongInstrumentalPartDTO mapToSongInstrumentalPartDTO(SongInstrumentalPart songInstrumentalPart);

    List<SongInstrumentalPartDTO> mapAllToSongInstrumentalPartDTOList(List<SongInstrumentalPart> songInstrumentalParts);

    default StageRoleDTO mapToStageRoleDTO(StageRole stageRole) {
        return StageRoleMapper.INSTANCE.mapToStageRoleDTO(stageRole);
    }
}
