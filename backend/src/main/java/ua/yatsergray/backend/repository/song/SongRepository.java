package ua.yatsergray.backend.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.song.Song;

import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {

    boolean existsByArtistIdAndName(UUID songArtistId, String songName);

    long countByArtistId(UUID artistId);

    long countByKeyId(UUID keyId);

    long countByKeysId(UUID keyId);

    long countByTimeSignatureId(UUID timeSignatureId);

    long countBySongCategoryId(UUID songCategoryId);

    long countBySongMoodId(UUID songMoodId);
}
