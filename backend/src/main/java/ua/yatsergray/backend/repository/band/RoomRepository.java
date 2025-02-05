package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.Room;

import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    boolean existsByBandIdAndName(UUID bandId, String roomName);

    boolean existsByBandIdAndId(UUID bandId, UUID roomId);
}
