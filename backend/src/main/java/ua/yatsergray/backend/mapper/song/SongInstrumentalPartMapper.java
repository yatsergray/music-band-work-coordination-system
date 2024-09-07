package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongInstrumentalPartDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongInstrumentalPartEditableDTO;
import ua.yatsergray.backend.domain.entity.song.SongInstrumentalPart;
import ua.yatsergray.backend.mapper.band.StageRoleMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StageRoleMapper.class})
public interface SongInstrumentalPartMapper {

    SongInstrumentalPartMapper INSTANCE = Mappers.getMapper(SongInstrumentalPartMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audioFileId", ignore = true)
    @Mapping(target = "videoFileId", ignore = true)
    @Mapping(target = "tabFileId", ignore = true)
    @Mapping(target = "song", ignore = true)
    @Mapping(target = "stageRole", ignore = true)
    SongInstrumentalPart mapToSongInstrumentalPart(SongInstrumentalPartEditableDTO songInstrumentalPartEditableDTO);

    @Mapping(source = "stageRole", target = "stageRoleDTO")
    SongInstrumentalPartDTO mapToSongInstrumentalPartDTO(SongInstrumentalPart songInstrumentalPart);

    @Mapping(source = "song.id", target = "songUUID")
    @Mapping(source = "stageRole.id", target = "stageRoleUUID")
    SongInstrumentalPartEditableDTO mapToSongInstrumentalPartEditableDTO(SongInstrumentalPart songInstrumentalPart);

    List<SongInstrumentalPartDTO> mapAllToSongInstrumentalPartDTOList(List<SongInstrumentalPart> songInstrumentalParts);
}
