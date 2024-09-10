package ua.yatsergray.backend.domain.entity.song;

import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "song_parts")
public class SongPart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "type_number")
    private Integer typeNumber;

    @Column(name = "measures_number", nullable = false)
    private Integer measuresNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song", nullable = false)
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song_part_category", nullable = false)
    private SongPartCategory songPartCategory;

    @OneToMany(mappedBy = "songPart")
    private Set<SongPartKeyChord> songPartKeyChords = new LinkedHashSet<>();

    @OneToMany(mappedBy = "songPart")
    private Set<SongPartDetails> songPartDetailsSet = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongPart songPart)) return false;
        return Objects.equals(id, songPart.id) && Objects.equals(text, songPart.text) && Objects.equals(typeNumber, songPart.typeNumber) && Objects.equals(measuresNumber, songPart.measuresNumber) && Objects.equals(song, songPart.song) && Objects.equals(songPartCategory, songPart.songPartCategory) && Objects.equals(songPartKeyChords, songPart.songPartKeyChords) && Objects.equals(songPartDetailsSet, songPart.songPartDetailsSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, typeNumber, measuresNumber, song, songPartCategory, songPartKeyChords, songPartDetailsSet);
    }
}
