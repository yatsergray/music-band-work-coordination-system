package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.mapper.FileURLToFileNameMapper;
import ua.yatsergray.backend.mapper.user.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ChatMapper.class, EventMapper.class, InvitationMapper.class, BandSongVersionMapper.class, UserMapper.class, FileURLToFileNameMapper.class})
public interface BandMapper {

    BandMapper INSTANCE = Mappers.getMapper(BandMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageFileURL", ignore = true)
    @Mapping(target = "chats", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "invitations", ignore = true)
    @Mapping(target = "bandSongVersions", ignore = true)
    @Mapping(target = "bandUserStageRoles", ignore = true)
    @Mapping(target = "bandUserAccessRoles", ignore = true)
    @Mapping(target = "users", ignore = true)
    Band mapToBand(BandEditableDTO bandEditableDTO);

    @Mapping(source = "chats", target = "chatDTOList")
    @Mapping(source = "events", target = "eventDTOList")
    @Mapping(source = "invitations", target = "invitationDTOList")
    @Mapping(source = "bandSongVersions", target = "bandSongVersionDTOList")
    @Mapping(source = "users", target = "userDTOList")
    BandDTO mapToBandDTO(Band band);

    @Mapping(source = "imageFileURL", target = "imageFileName", qualifiedByName = "fileURLToFileName")
    BandEditableDTO mapToBandEditableDTO(Band band);

    List<BandDTO> mapAllToBandDTOList(List<Band> bands);
}
