package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.MusicBandDTO;
import ua.yatsergray.backend.v2.domain.dto.MusicBandUserDTO;
import ua.yatsergray.backend.v2.domain.entity.MusicBand;
import ua.yatsergray.backend.v2.domain.entity.MusicBandUserAccessRole;
import ua.yatsergray.backend.v2.domain.entity.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = {InvitationMapper.class, ChatMapper.class, SongMapper.class, EventMapper.class})
public interface MusicBandMapper {

    MusicBandMapper INSTANCE = Mappers.getMapper(MusicBandMapper.class);

    @Mapping(source = "invitations", target = "invitationDTOList")
    @Mapping(target = "musicBandUserDTOList", expression = "java(mapAllToMusicBandUserDTOList(musicBand))")
    @Mapping(source = "chats", target = "chatDTOList")
    @Mapping(source = "songs", target = "songDTOList")
    @Mapping(source = "events", target = "eventDTOList")
    MusicBandDTO mapToMusicBandDTO(MusicBand musicBand);

    List<MusicBandDTO> mapAllToMusicBandDTOList(List<MusicBand> musicBands);

    default List<MusicBandUserDTO> mapAllToMusicBandUserDTOList(MusicBand musicBand) {
        return MusicBandUserMapper.INSTANCE.mapAllToMusicBandUserDTOList(musicBand, mapMusicBandUserAccessRolesToUsers(musicBand.getMusicBandUserAccessRoles().stream().toList()));
    }

    default List<User> mapMusicBandUserAccessRolesToUsers(List<MusicBandUserAccessRole> musicBandUserAccessRoles) {
        return musicBandUserAccessRoles.stream()
                .map(MusicBandUserAccessRole::getUser)
                .distinct()
                .toList();
    }
}
