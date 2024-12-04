package ua.yatsergray.backend.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.song.SongMood;

import java.util.UUID;

@Repository
public interface SongMoodRepository extends JpaRepository<SongMood, UUID> {

    boolean existsByBandIdAndName(UUID bandId, String songMoodName);
}
