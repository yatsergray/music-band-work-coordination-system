package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.BandAccessRole;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BandAccessRoleMapper {

    BandAccessRoleMapper INSTANCE = Mappers.getMapper(BandAccessRoleMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bandUserAccessRoles", ignore = true)
    BandAccessRole mapToBandAccessRole(BandAccessRoleEditableDTO bandAccessRoleEditableDTO);

    BandAccessRoleDTO mapToBandAccessRoleDTO(BandAccessRole bandAccessRole);

    BandAccessRoleEditableDTO mapToBandAccessRoleEditableDTO(BandAccessRole bandAccessRole);

    List<BandAccessRoleDTO> mapAllToBandAccessRoleDTOList(List<BandAccessRole> bandAccessRoles);
}

