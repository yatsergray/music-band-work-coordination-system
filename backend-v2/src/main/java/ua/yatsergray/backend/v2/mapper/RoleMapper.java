package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import ua.yatsergray.backend.v2.domain.dto.RoleDTO;
import ua.yatsergray.backend.v2.domain.entity.Role;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDTO mapToRoleDTO(Role role);

    List<RoleDTO> mapAllToRoleDTOList(List<Role> roles);
}
