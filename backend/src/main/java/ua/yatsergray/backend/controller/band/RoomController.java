package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.RoomDTO;
import ua.yatsergray.backend.domain.request.band.RoomCreateRequest;
import ua.yatsergray.backend.domain.request.band.RoomUpdateRequest;
import ua.yatsergray.backend.service.band.impl.RoomServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private final RoomServiceImpl roomService;

    @Autowired
    public RoomController(RoomServiceImpl roomService) {
        this.roomService = roomService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomCreateRequest roomCreateRequest) {
        return ResponseEntity.ok(roomService.addRoom(roomCreateRequest));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> readRoomById(@PathVariable("roomId") UUID roomId) {
        return roomService.getRoomById(roomId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> readAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @SneakyThrows
    @PutMapping("/{roomId}")
    public ResponseEntity<RoomDTO> updateRoomById(@PathVariable("roomId") UUID roomId, @Valid @RequestBody RoomUpdateRequest roomUpdateRequest) {
        return ResponseEntity.ok(roomService.modifyRoomById(roomId, roomUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable("roomId") UUID roomId) {
        roomService.removeRoomById(roomId);

        return ResponseEntity.ok().build();
    }
}
