package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.RoomDTO;
import ua.yatsergray.backend.domain.request.band.RoomCreateRequest;
import ua.yatsergray.backend.domain.request.band.RoomUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.RoomAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchRoomException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomService {

    RoomDTO addRoom(RoomCreateRequest roomCreateRequest) throws NoSuchBandException, RoomAlreadyExistsException;

    Optional<RoomDTO> getRoomById(UUID roomId);

    List<RoomDTO> getAllRooms();

    RoomDTO modifyRoomById(UUID roomId, RoomUpdateRequest roomUpdateRequest) throws NoSuchRoomException, RoomAlreadyExistsException;

    void removeRoomById(UUID roomId) throws NoSuchRoomException, ChildEntityExistsException;
}
