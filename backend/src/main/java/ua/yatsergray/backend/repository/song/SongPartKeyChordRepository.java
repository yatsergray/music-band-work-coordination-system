package ua.yatsergray.backend.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.song.SongPartKeyChord;

import java.util.List;
import java.util.UUID;

@Repository
public interface SongPartKeyChordRepository extends JpaRepository<SongPartKeyChord, UUID> {

    boolean existsBySongPartIdAndKeyIdAndSequenceNumber(UUID songPartId, UUID keyId, Integer sequenceNumber);

    long countByChordId(UUID chordId);

    long countByKeyId(UUID keyId);

    @Query(value = """
            SELECT spkc.id, spkc.sequence_number, spkc.id_key, spkc.id_chord, spkc.id_song_part
            FROM songs s
                JOIN song_parts sp ON sp.id_song = s.id
                JOIN song_part_key_chords spkc ON spkc.id_song_part = sp.id AND spkc.id NOT IN (
                    SELECT spkc1.id
                    FROM songs s1
                        JOIN band_song_versions bsv ON bsv.id_song = s1.id
                        JOIN song_part_details spd ON spd.id_band_song_version = bsv.id
                        JOIN song_parts sp1 ON sp1.id = spd.id_song_part
                        JOIN song_part_key_chords spkc1 ON spkc1.id_song_part = sp1.id AND spkc1.id_key = bsv.id_key
                    WHERE s1.id = s.id
                )
            WHERE s.id = :songId
            """, nativeQuery = true)
    List<SongPartKeyChord> findAvailableToDeleteBySongId(@Param("songId") UUID songId);

    @Query(value = """
            SELECT spkc.id, spkc.sequence_number, spkc.id_key, spkc.id_chord, spkc.id_song_part
            FROM band_song_versions bsv
                JOIN song_part_details spd ON spd.id_band_song_version = bsv.id
                JOIN song_parts sp ON sp.id = spd.id_song_part AND sp.id_song IS NULL
                JOIN song_part_key_chords spkc ON spkc.id_song_part = sp.id AND spkc.id_key = bsv.id_key AND spkc.id NOT IN (
                    SELECT spkc1.id
                    FROM band_song_versions bsv1
                        JOIN song_part_details spd1 ON spd1.id_band_song_version = bsv1.id
                        JOIN (SELECT sp1.id
                              FROM band_song_versions bsv2
                                  JOIN song_part_details spd2 ON spd2.id_band_song_version = bsv2.id
                                  JOIN song_parts sp1 ON sp1.id = spd2.id_song_part AND sp1.id_song IS NULL
                              WHERE bsv2.id = bsv.id) AS sp1 ON sp1.id = spd1.id_song_part
                        JOIN song_part_key_chords spkc1 ON spkc1.id_song_part = sp1.id AND spkc1.id_key = bsv1.id_key
                    WHERE bsv1.id != bsv.id AND bsv1.id_key = bsv.id_key
                )
            WHERE bsv.id = :bandSongVersionId
            """, nativeQuery = true)
    List<SongPartKeyChord> findAvailableToDeleteByBandSongVersionId(@Param("bandSongVersionId") UUID bandSongVersionId);

    @Query(value = """
            SELECT spkc.id, spkc.sequence_number, spkc.id_key, spkc.id_chord, spkc.id_song_part
            FROM song_parts sp
                JOIN song_part_key_chords spkc ON spkc.id_song_part = sp.id AND spkc.id NOT IN (
                    SELECT spkc1.id
                    FROM band_song_versions bsv
                        JOIN song_part_details spd ON spd.id_band_song_version = bsv.id
                        JOIN song_parts sp1 ON sp1.id = spd.id_song_part AND sp1.id = sp.id
                        JOIN song_part_key_chords spkc1 ON spkc1.id_song_part = sp1.id AND spkc1.id_key = bsv.id_key
                )
            WHERE sp.id = :songPartId
            """, nativeQuery = true)
    List<SongPartKeyChord> findAvailableToDeleteBySongPartId(@Param("songPartId") UUID songPartId);
}
