package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.entity.band.BandAccessRole;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BandAccessRoleMapper {

    BandAccessRoleMapper INSTANCE = Mappers.getMapper(BandAccessRoleMapper.class);

    BandAccessRoleDTO mapToBandAccessRoleDTO(BandAccessRole bandAccessRole);

    List<BandAccessRoleDTO> mapAllToBandAccessRoleDTOList(List<BandAccessRole> bandAccessRoles);
}

