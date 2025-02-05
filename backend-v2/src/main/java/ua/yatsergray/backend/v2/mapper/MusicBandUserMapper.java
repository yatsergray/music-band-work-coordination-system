package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.MusicBandAccessRoleDTO;
import ua.yatsergray.backend.v2.domain.dto.MusicBandUserDTO;
import ua.yatsergray.backend.v2.domain.dto.StageRoleDTO;
import ua.yatsergray.backend.v2.domain.dto.UserDTO;
import ua.yatsergray.backend.v2.domain.entity.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mapper
public interface MusicBandUserMapper {

    MusicBandUserMapper INSTANCE = Mappers.getMapper(MusicBandUserMapper.class);

    @Mapping(target = "userDTO", expression = "java(mapToUserDTO(user))")
    @Mapping(target = "musicBandAccessRoleDTOList", expression = "java(mapAllToMusicBandAccessRoleDTOList(getMusicBandAccessRolesFromMusicBandAndUser(musicBand, user)))")
    @Mapping(target = "stageRoleDTOList", expression = "java(mapAllToStageRoleDTOList(getStageRolesFromMusicBandAndUser(musicBand, user)))")
    MusicBandUserDTO mapToMusicBandUserDTO(MusicBand musicBand, User user);

    default List<MusicBandUserDTO> mapAllToMusicBandUserDTOList(MusicBand musicBand, List<User> users) {
        return users.stream()
                .map(user -> mapToMusicBandUserDTO(musicBand, user))
                .toList();
    }

    default UserDTO mapToUserDTO(User user) {
        return UserMapper.INSTANCE.mapToUserDTO(user);
    }

    default List<MusicBandAccessRoleDTO> mapAllToMusicBandAccessRoleDTOList(List<MusicBandAccessRole> musicBandAccessRoles) {
        return MusicBandAccessRoleMapper.INSTANCE.mapAllToMusicBandAccessRoleDTOList(musicBandAccessRoles);
    }

    default List<StageRoleDTO> mapAllToStageRoleDTOList(List<StageRole> stageRoles) {
        return StageRoleMapper.INSTANCE.mapAllToStageRoleDTOList(stageRoles);
    }

    default List<MusicBandAccessRole> getMusicBandAccessRolesFromMusicBandAndUser(MusicBand musicBand, User user) {
        if (Objects.isNull(musicBand) || Objects.isNull(user)) {
            return Collections.emptyList();
        }

        return user.getMusicBandUserAccessRoles().stream()
                .filter(musicBandUserAccessRole -> musicBandUserAccessRole.getMusicBand().equals(musicBand))
                .map(MusicBandUserAccessRole::getMusicBandAccessRole)
                .toList();
    }

    default List<StageRole> getStageRolesFromMusicBandAndUser(MusicBand musicBand, User user) {
        if (Objects.isNull(user)) {
            return Collections.emptyList();
        }

        return user.getMusicBandUserStageRoles().stream()
                .filter(musicBandUserStageRole -> musicBandUserStageRole.getMusicBand().equals(musicBand))
                .map(MusicBandUserStageRole::getStageRole)
                .toList();
    }
}
