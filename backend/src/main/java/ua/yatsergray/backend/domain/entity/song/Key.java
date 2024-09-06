package ua.yatsergray.backend.domain.entity.song;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;

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
@Table(name = "keys")
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "key")
    private Set<BandSongVersion> bandSongVersions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "key")
    private Set<Song> songsWithOriginalKey = new LinkedHashSet<>();

    @OneToMany(mappedBy = "key")
    private Set<SongPartKeyChord> songPartKeyChords = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "song_keys",
            joinColumns = {@JoinColumn(name = "id_key")},
            inverseJoinColumns = {@JoinColumn(name = "id_song")}
    )
    private Set<Song> songs = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key key)) return false;
        return Objects.equals(id, key.id) && Objects.equals(name, key.name) && Objects.equals(bandSongVersions, key.bandSongVersions) && Objects.equals(songsWithOriginalKey, key.songsWithOriginalKey) && Objects.equals(songPartKeyChords, key.songPartKeyChords) && Objects.equals(songs, key.songs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bandSongVersions, songsWithOriginalKey, songPartKeyChords, songs);
    }
}
