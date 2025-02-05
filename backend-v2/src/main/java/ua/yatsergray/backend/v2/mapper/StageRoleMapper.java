package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.StageRoleDTO;
import ua.yatsergray.backend.v2.domain.entity.StageRole;

import java.util.List;

@Mapper
public interface StageRoleMapper {

    StageRoleMapper INSTANCE = Mappers.getMapper(StageRoleMapper.class);

    StageRoleDTO mapToStageRoleDTO(StageRole stageRole);

    List<StageRoleDTO> mapAllToStageRoleDTOList(List<StageRole> stageRoles);
}
