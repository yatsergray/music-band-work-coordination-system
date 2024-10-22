package ua.yatsergray.backend.mapper.user;

import org.mapstruct.Mapper;
import ua.yatsergray.backend.domain.dto.user.RoleDTO;
import ua.yatsergray.backend.domain.entity.user.Role;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDTO mapToRoleDTO(Role role);

    List<RoleDTO> mapAllToRoleDTOList(List<Role> roles);
}
