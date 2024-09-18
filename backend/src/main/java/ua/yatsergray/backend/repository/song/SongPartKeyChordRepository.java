package ua.yatsergray.backend.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.song.SongPartKeyChord;

import java.util.UUID;

@Repository
public interface SongPartKeyChordRepository extends JpaRepository<SongPartKeyChord, UUID> {

    boolean existsBySongPartIdAndKeyIdAndSequenceNumber(UUID songPartId, UUID keyId, Integer sequenceNumber);
}
