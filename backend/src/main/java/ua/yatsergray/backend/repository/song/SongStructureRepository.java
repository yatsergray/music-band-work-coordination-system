package ua.yatsergray.backend.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.song.SongStructure;

import java.util.UUID;

@Repository
public interface SongStructureRepository extends JpaRepository<SongStructure, UUID> {
}
