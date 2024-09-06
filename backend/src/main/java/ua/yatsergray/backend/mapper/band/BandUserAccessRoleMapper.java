package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.editable.BandUserAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.BandUserAccessRole;

@Mapper(componentModel = "spring")
public interface BandUserAccessRoleMapper {

    BandUserAccessRoleMapper INSTANCE = Mappers.getMapper(BandUserAccessRoleMapper.class);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "band", ignore = true)
//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "accessRole", ignore = true)
//    NoSuchBandUserAccessRoleException mapToBandUserAccessRole(BandUserAccessRoleEditableDTO bandUserAccessRoleEditableDTO);

    @Mapping(source = "band.id", target = "bandUUID")
    @Mapping(source = "user.id", target = "userUUID")
    @Mapping(source = "bandAccessRole.id", target = "bandAccessRoleUUID")
    BandUserAccessRoleEditableDTO mapToBandUserAccessRoleEditableDTO(BandUserAccessRole bandUserAccessRole);
}
