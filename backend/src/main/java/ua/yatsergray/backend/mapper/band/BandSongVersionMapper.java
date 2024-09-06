package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.BandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandSongVersionEditableDTO;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;
import ua.yatsergray.backend.mapper.FileURLToFileNameMapper;
import ua.yatsergray.backend.mapper.song.KeyMapper;
import ua.yatsergray.backend.mapper.song.SongMapper;
import ua.yatsergray.backend.mapper.song.SongStructureMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {KeyMapper.class, SongMapper.class, SongStructureMapper.class, FileURLToFileNameMapper.class})
public interface BandSongVersionMapper {

    BandSongVersionMapper INSTANCE = Mappers.getMapper(BandSongVersionMapper.class);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "audioFileURL", ignore = true)
//    @Mapping(target = "key", ignore = true)
//    @Mapping(target = "band", ignore = true)
//    @Mapping(target = "song", ignore = true)
//    @Mapping(target = "songStructure", ignore = true)
//    @Mapping(target = "eventBandSongVersions", ignore = true)
//    BandSongVersion mapToBandSongVersion(BandSongVersionEditableDTO bandSongVersionEditableDTO);

    @Mapping(source = "key", target = "keyDTO")
    @Mapping(source = "song", target = "songDTO")
    @Mapping(source = "songStructure", target = "songStructureDTO")
    BandSongVersionDTO mapToBandSongVersionDTO(BandSongVersion bandSongVersion);

    @Mapping(source = "audioFileURL", target = "audioFileName", qualifiedByName = "fileURLToFileName")
    @Mapping(source = "key.id", target = "keyUUID")
    @Mapping(source = "band.id", target = "bandUUID")
    @Mapping(source = "song.id", target = "songUUID")
    @Mapping(source = "songStructure.id", target = "songStructureUUID")
    BandSongVersionEditableDTO mapToBandSongVersionEditableDTO(BandSongVersion bandSongVersion);

    List<BandSongVersionDTO> mapAllToBandSongVersionDTOList(List<BandSongVersion> bandSongVersions);
}
