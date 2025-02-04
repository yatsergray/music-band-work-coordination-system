package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.UserDTO;
import ua.yatsergray.backend.v2.domain.entity.User;

import java.util.List;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "roles", target = "roleDTOList")
    UserDTO mapToUserDTO(User user);

    List<UserDTO> mapAllToUserDTOList(List<User> users);
}
