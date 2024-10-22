package ua.yatsergray.backend.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.song.SongPart;

import java.util.List;
import java.util.UUID;

@Repository
public interface SongPartRepository extends JpaRepository<SongPart, UUID> {

    boolean existsByIdAndSongId(UUID songPartId, UUID songPartSongId);

    boolean existsBySongIdAndSongPartCategoryIdAndTypeNumber(UUID songId, UUID songPartCategoryId, Integer typeNumber);

    long countBySongPartCategoryId(UUID songPartCategoryId);

    @Query(value = """
            SELECT sp.id, sp.text, sp.type_number, sp.measures_number, sp.id_song, sp.id_song_part_category
            FROM songs s
                JOIN song_parts sp ON sp.id_song = s.id AND sp.id NOT IN (
                    SELECT sp1.id
                    FROM songs s1
                        JOIN song_parts sp1 ON sp1.id_song = s1.id
                        JOIN song_part_details spd ON spd.id_song_part = sp1.id AND spd.id_band_song_version IS NOT NULL
                    WHERE s1.id = s.id
                )
            WHERE s.id = :songId
            """, nativeQuery = true)
    List<SongPart> findAvailableToDeleteBySongId(@Param("songId") UUID songId);

    @Query(value = """
            SELECT sp.id, sp.text, sp.type_number, sp.measures_number, sp.id_song, sp.id_song_part_category
            FROM band_song_versions bsv
                JOIN song_part_details spd ON spd.id_band_song_version = bsv.id
                JOIN song_parts sp ON sp.id = spd.id_song_part AND sp.id_song IS NULL AND sp.id NOT IN (
                    SELECT sp1.id
                    FROM band_song_versions bsv1
                        JOIN song_part_details spd2 ON spd2.id_band_song_version = bsv1.id
                        JOIN (SELECT sp1.id
                              FROM band_song_versions bsv1
                                  JOIN song_part_details spd2 ON spd2.id_band_song_version = bsv1.id
                                  JOIN song_parts sp1 ON sp1.id = spd2.id_song_part AND sp1.id_song IS NULL
                              WHERE bsv1.id = bsv.id) AS sp1 ON sp1.id = spd2.id_song_part
                    WHERE bsv1.id != bsv.id
                )
            WHERE bsv.id = :bandSongVersionId
            """, nativeQuery = true)
    List<SongPart> findAvailableToDeleteByBandSongVersionId(@Param("bandSongVersionId") UUID bandSongVersionId);

    @Query(value = """
            SELECT COUNT(bsv) > 0
            FROM song_parts sp
                JOIN song_part_details spd ON spd.id_song_part = sp.id
                JOIN band_song_versions bsv ON bsv.id = spd.id_band_song_version
            WHERE sp.id = :songPartId
            """, nativeQuery = true)
    boolean isNotAvailableToDelete(@Param("songPartId") UUID songPartId);
}
