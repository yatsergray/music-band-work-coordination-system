package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.BandUserDTO;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.entity.band.*;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.mapper.user.UserMapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mapper
public interface BandUserMapper {

    BandUserMapper INSTANCE = Mappers.getMapper(BandUserMapper.class);

    @Mapping(target = "userDTO", expression = "java(mapToUserDTO(user))")
    @Mapping(target = "bandAccessRoleDTOList", expression = "java(mapAllToBandAccessRoleDTOList(getBandAccessRolesFromBandAndUser(band, user)))")
    @Mapping(target = "stageRoleDTOList", expression = "java(mapAllToStageRoleDTOList(getStageRolesFromBandAndUser(band, user)))")
    BandUserDTO mapToBandUserDTO(Band band, User user);

    default List<BandUserDTO> mapAllToBandUserDTOList(Band band, List<User> users) {
        return users.stream()
                .map(user -> mapToBandUserDTO(band, user))
                .toList();
    }

    default UserDTO mapToUserDTO(User user) {
        return UserMapper.INSTANCE.mapToUserDTO(user);
    }

    default List<BandAccessRoleDTO> mapAllToBandAccessRoleDTOList(List<BandAccessRole> bandAccessRoles) {
        return BandAccessRoleMapper.INSTANCE.mapAllToBandAccessRoleDTOList(bandAccessRoles);
    }

    default List<StageRoleDTO> mapAllToStageRoleDTOList(List<StageRole> stageRoles) {
        return StageRoleMapper.INSTANCE.mapAllToStageRoleDTOList(stageRoles);
    }

    default List<BandAccessRole> getBandAccessRolesFromBandAndUser(Band band, User user) {
        if (Objects.isNull(band) || Objects.isNull(user)) {
            return Collections.emptyList();
        }

        return user.getBandUserAccessRoles().stream()
                .filter(bandUserAccessRole -> bandUserAccessRole.getBand().equals(band))
                .map(BandUserAccessRole::getBandAccessRole)
                .toList();
    }

    default List<StageRole> getStageRolesFromBandAndUser(Band band, User user) {
        if (Objects.isNull(user)) {
            return Collections.emptyList();
        }

        return user.getBandUserStageRoles().stream()
                .filter(bandUserStageRole -> bandUserStageRole.getBand().equals(band))
                .map(BandUserStageRole::getStageRole)
                .toList();
    }
}
