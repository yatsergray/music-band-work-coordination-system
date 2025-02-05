package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.dto.band.BandUserDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.BandUserAccessRole;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.mapper.song.ArtistMapper;
import ua.yatsergray.backend.mapper.song.SongCategoryMapper;
import ua.yatsergray.backend.mapper.song.SongMapper;
import ua.yatsergray.backend.mapper.song.SongMoodMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ChatMapper.class, EventMapper.class, RoomMapper.class, SongCategoryMapper.class, SongMoodMapper.class, InvitationMapper.class, BandSongVersionMapper.class, SongMapper.class, ArtistMapper.class})
public interface BandMapper {

    BandMapper INSTANCE = Mappers.getMapper(BandMapper.class);

    @Mapping(source = "chats", target = "chatDTOList")
    @Mapping(source = "events", target = "eventDTOList")
    @Mapping(source = "rooms", target = "roomDTOList")
    @Mapping(source = "songCategories", target = "songCategoryDTOList")
    @Mapping(source = "songMoods", target = "songMoodDTOList")
    @Mapping(source = "invitations", target = "invitationDTOList")
    @Mapping(source = "bandSongVersions", target = "bandSongVersionDTOList")
    @Mapping(target = "bandUserDTOList", expression = "java(mapAllToBandUserDTOList(band))")
    @Mapping(source = "songs", target = "songDTOList")
    @Mapping(source = "artists", target = "artistDTOList")
    BandDTO mapToBandDTO(Band band);

    List<BandDTO> mapAllToBandDTOList(List<Band> bands);

    default List<BandUserDTO> mapAllToBandUserDTOList(Band band) {
        return BandUserMapper.INSTANCE.mapAllToBandUserDTOList(band, mapBandUserAccessRolesToUsers(band.getBandUserAccessRoles().stream().toList()));
    }

    default List<User> mapBandUserAccessRolesToUsers(List<BandUserAccessRole> bandUserAccessRoles) {
        return bandUserAccessRoles.stream()
                .map(BandUserAccessRole::getUser)
                .distinct()
                .toList();
    }
}
