package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.editable.BandUserStageRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.BandUserStageRole;

@Mapper(componentModel = "spring")
public interface BandUserStageRoleMapper {

    BandUserStageRoleMapper INSTANCE = Mappers.getMapper(BandUserStageRoleMapper.class);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "band", ignore = true)
//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "stageRole", ignore = true)
//    NoSuchBandUserStageRoleException mapToBandUserStageRole(BandUserStageRoleEditableDTO bandUserStageRoleEditableDTO);

    @Mapping(source = "band.id", target = "bandUUID")
    @Mapping(source = "user.id", target = "userUUID")
    @Mapping(source = "stageRole.id", target = "stageRoleUUID")
    BandUserStageRoleEditableDTO mapToBandUserStageRoleEditableDTO(BandUserStageRole bandUserStageRole);
}
