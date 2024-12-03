package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.RoomDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Room;
import ua.yatsergray.backend.domain.request.band.RoomCreateRequest;
import ua.yatsergray.backend.domain.request.band.RoomUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchRoomException;
import ua.yatsergray.backend.exception.band.RoomAlreadyExistsException;
import ua.yatsergray.backend.mapper.band.RoomMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.band.EventRepository;
import ua.yatsergray.backend.repository.band.RoomRepository;
import ua.yatsergray.backend.service.band.RoomService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;
    private final BandRepository bandRepository;
    private final EventRepository eventRepository;

    @Autowired
    public RoomServiceImpl(RoomMapper roomMapper, RoomRepository roomRepository, BandRepository bandRepository, EventRepository eventRepository) {
        this.roomMapper = roomMapper;
        this.roomRepository = roomRepository;
        this.bandRepository = bandRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public RoomDTO addRoom(RoomCreateRequest roomCreateRequest) throws NoSuchBandException, RoomAlreadyExistsException {
        Band band = bandRepository.findById(roomCreateRequest.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", roomCreateRequest.getBandId())));

        if (roomRepository.existsByBandIdAndName(band.getId(), roomCreateRequest.getName())) {
            throw new RoomAlreadyExistsException(String.format("Room with bandId=\"%s\" and name=\"%s\" already exists", band.getId(), roomCreateRequest.getName()));
        }

        Room room = Room.builder()
                .name(roomCreateRequest.getName())
                .band(band)
                .build();

        return roomMapper.mapToRoomDTO(roomRepository.save(room));
    }

    @Override
    public Optional<RoomDTO> getRoomById(UUID roomId) {
        return roomRepository.findById(roomId).map(roomMapper::mapToRoomDTO);
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomMapper.mapAllToRoomDTOList(roomRepository.findAll());
    }

    @Override
    public RoomDTO modifyRoomById(UUID roomId, RoomUpdateRequest roomUpdateRequest) throws NoSuchRoomException, RoomAlreadyExistsException {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchRoomException(String.format("Room with id=\"%s\" does not exist", roomId)));

        if (!roomUpdateRequest.getName().equals(room.getName()) && roomRepository.existsByBandIdAndName(room.getBand().getId(), roomUpdateRequest.getName())) {
            throw new RoomAlreadyExistsException(String.format("Room with bandId=\"%s\" and name=\"%s\" already exists", room.getBand().getId(), roomUpdateRequest.getName()));
        }

        room.setName(roomUpdateRequest.getName());

        return roomMapper.mapToRoomDTO(roomRepository.save(room));
    }

    @Override
    public void removeRoomById(UUID roomId) throws NoSuchRoomException, ChildEntityExistsException {
        if (!roomRepository.existsById(roomId)) {
            throw new NoSuchRoomException(String.format("Room with id=\"%s\" does not exist", roomId));
        }

        checkIfRoomHasChildEntity(roomId);

        roomRepository.deleteById(roomId);
    }

    private void checkIfRoomHasChildEntity(UUID roomId) throws ChildEntityExistsException {
        long roomChildEntityAmount = eventRepository.countByRoomId(roomId);

        if (roomChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Event(s) depend(s) on the Room with id=\"%s\"", roomChildEntityAmount, roomId));
        }
    }
}
