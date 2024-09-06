package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.StageRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.StageRole;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StageRoleMapper {

    StageRoleMapper INSTANCE = Mappers.getMapper(StageRoleMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventUsers", ignore = true)
    @Mapping(target = "bandUserStageRoles", ignore = true)
    @Mapping(target = "songInstrumentalParts", ignore = true)
    StageRole mapToStageRole(StageRoleEditableDTO stageRoleEditableDTO);

    StageRoleDTO mapToStageRoleDTO(StageRole stageRole);

    StageRoleEditableDTO mapToStageRoleEditableDTO(StageRole stageRole);

    List<StageRoleDTO> mapAllToStageRoleDTOList(List<StageRole> stageRoles);
}
