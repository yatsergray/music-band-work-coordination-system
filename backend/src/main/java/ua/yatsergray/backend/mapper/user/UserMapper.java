package ua.yatsergray.backend.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.entity.user.User;

import java.util.List;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "roles", target = "roleDTOList")
    UserDTO mapToUserDTO(User user);

    List<UserDTO> mapAllToUserDTOList(List<User> users);
}
