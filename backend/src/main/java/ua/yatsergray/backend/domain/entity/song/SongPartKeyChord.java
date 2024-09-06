package ua.yatsergray.backend.domain.entity.song;

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
@Table(name = "song_part_key_chords")
public class SongPartKeyChord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_key", nullable = false)
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chord", nullable = false)
    private Chord chord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song_part", nullable = false)
    private SongPart songPart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongPartKeyChord that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(sequenceNumber, that.sequenceNumber) && Objects.equals(key, that.key) && Objects.equals(chord, that.chord) && Objects.equals(songPart, that.songPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequenceNumber, key, chord, songPart);
    }
}
