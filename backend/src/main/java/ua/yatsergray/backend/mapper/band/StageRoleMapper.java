package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;
import ua.yatsergray.backend.domain.entity.band.StageRole;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StageRoleMapper {

    StageRoleMapper INSTANCE = Mappers.getMapper(StageRoleMapper.class);

    StageRoleDTO mapToStageRoleDTO(StageRole stageRole);

    List<StageRoleDTO> mapAllToStageRoleDTOList(List<StageRole> stageRoles);
}
