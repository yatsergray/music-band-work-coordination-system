package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.RoomDTO;
import ua.yatsergray.backend.domain.entity.band.Room;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomDTO mapToRoomDTO(Room room);

    List<RoomDTO> mapAllToRoomDTOList(List<Room> rooms);
}
