package ua.yatsergray.backend.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.song.TimeSignature;

import java.util.UUID;

@Repository
public interface TimeSignatureRepository extends JpaRepository<TimeSignature, UUID> {

    boolean existsByBeatsAndDuration(Integer timeSignatureBeats, Integer timeSignatureDuration);
}
