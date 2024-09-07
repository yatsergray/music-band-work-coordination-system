package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.SongStructure;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "band_song_versions")
public class BandSongVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "audio_file_id", unique = true, nullable = false)
    private UUID audioFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_key", nullable = false)
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_band", nullable = false)
    private Band band;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song", nullable = false)
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song_structure", nullable = false)
    private SongStructure songStructure;

    @OneToMany(mappedBy = "bandSongVersion")
    private Set<EventBandSongVersion> eventBandSongVersions = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BandSongVersion that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(audioFileId, that.audioFileId) && Objects.equals(key, that.key) && Objects.equals(band, that.band) && Objects.equals(song, that.song) && Objects.equals(songStructure, that.songStructure) && Objects.equals(eventBandSongVersions, that.eventBandSongVersions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audioFileId, key, band, song, songStructure, eventBandSongVersions);
    }
}
