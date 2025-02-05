package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.MusicBandAccessRoleDTO;
import ua.yatsergray.backend.v2.domain.entity.MusicBandAccessRole;

import java.util.List;

@Mapper
public interface MusicBandAccessRoleMapper {

    MusicBandAccessRoleMapper INSTANCE = Mappers.getMapper(MusicBandAccessRoleMapper.class);

    MusicBandAccessRoleDTO mapToMusicBandAccessRoleDTO(MusicBandAccessRole musicBandAccessRole);

    List<MusicBandAccessRoleDTO> mapAllToMusicBandAccessRoleDTOList(List<MusicBandAccessRole> musicBandAccessRoles);
}

