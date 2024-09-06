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
@Table(name = "song_part_structure_details")
public class SongPartStructureDetails {

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
    @JoinColumn(name = "id_song_structure", nullable = false)
    private SongStructure songStructure;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongPartStructureDetails that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(sequenceNumber, that.sequenceNumber) && Objects.equals(repeatNumber, that.repeatNumber) && Objects.equals(songPart, that.songPart) && Objects.equals(songStructure, that.songStructure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequenceNumber, repeatNumber, songPart, songStructure);
    }
}
