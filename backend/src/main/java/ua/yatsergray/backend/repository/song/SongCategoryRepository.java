package ua.yatsergray.backend.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.song.SongCategory;

import java.util.UUID;

@Repository
public interface SongCategoryRepository extends JpaRepository<SongCategory, UUID> {

    boolean existsByBandIdAndName(UUID bandId, String songCategoryName);
}
