package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "event_band_song_versions")
public class EventBandSongVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_event", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song_version", nullable = false)
    private BandSongVersion bandSongVersion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventBandSongVersion that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(sequenceNumber, that.sequenceNumber) && Objects.equals(event, that.event) && Objects.equals(bandSongVersion, that.bandSongVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequenceNumber, event, bandSongVersion);
    }
}
