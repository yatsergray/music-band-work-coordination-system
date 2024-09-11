package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.mapper.user.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ChatMapper.class, EventMapper.class, InvitationMapper.class, BandSongVersionMapper.class, UserMapper.class})
public interface BandMapper {

    BandMapper INSTANCE = Mappers.getMapper(BandMapper.class);

    @Mapping(source = "chats", target = "chatDTOList")
    @Mapping(source = "events", target = "eventDTOList")
    @Mapping(source = "invitations", target = "invitationDTOList")
    @Mapping(source = "bandSongVersions", target = "bandSongVersionDTOList")
    @Mapping(source = "users", target = "userDTOList")
    BandDTO mapToBandDTO(Band band);

    List<BandDTO> mapAllToBandDTOList(List<Band> bands);
}
