package ua.yatsergray.backend.domain.entity.song;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "song_part_details")
public class SongPartDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @Column(name = "repeats_number", nullable = false)
    private Integer repeatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song_part", nullable = false)
    private SongPart songPart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song", nullable = false)
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_band_song_version", nullable = false)
    private BandSongVersion bandSongVersion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongPartDetails that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(sequenceNumber, that.sequenceNumber) && Objects.equals(repeatNumber, that.repeatNumber) && Objects.equals(songPart, that.songPart) && Objects.equals(song, that.song) && Objects.equals(bandSongVersion, that.bandSongVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequenceNumber, repeatNumber, songPart, song, bandSongVersion);
    }
}
