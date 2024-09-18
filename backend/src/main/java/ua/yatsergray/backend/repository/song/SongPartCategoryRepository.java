package ua.yatsergray.backend.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.song.SongPartCategory;
import ua.yatsergray.backend.domain.type.song.SongPartCategoryType;

import java.util.UUID;

@Repository
public interface SongPartCategoryRepository extends JpaRepository<SongPartCategory, UUID> {

    boolean existsByName(String songPartCategoryName);

    boolean existsByType(SongPartCategoryType songPartCategoryType);
}
