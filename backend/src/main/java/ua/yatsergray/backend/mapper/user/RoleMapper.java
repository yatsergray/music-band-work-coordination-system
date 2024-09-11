package ua.yatsergray.backend.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.user.RoleDTO;
import ua.yatsergray.backend.domain.entity.user.Role;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO mapToRoleDTO(Role role);

    List<RoleDTO> mapAllToRoleDTOList(List<Role> roles);
}
