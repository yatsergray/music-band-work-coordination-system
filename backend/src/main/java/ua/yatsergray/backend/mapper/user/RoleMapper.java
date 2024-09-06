package ua.yatsergray.backend.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.user.RoleDTO;
import ua.yatsergray.backend.domain.dto.user.editable.RoleEditableDTO;
import ua.yatsergray.backend.domain.entity.user.Role;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role mapToRole(RoleEditableDTO roleEditableDTO);

    RoleDTO mapToRoleDTO(Role role);

    RoleEditableDTO mapToRoleEditableDTO(Role role);

    List<RoleDTO> mapAllToRoleDTOList(List<Role> roles);
}
