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
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "image_file_url", unique = true)
    private String imageFileURL;

    @Column(name = "audio_file_url", unique = true, nullable = false)
    private String audioFileURL;

    @Column(name = "media_url", unique = true)
    private String mediaURL;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "bpm", nullable = false)
    private Integer bpm;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_song_structure", referencedColumnName = "id")
    private SongStructure songStructure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_key", nullable = false)
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_artist", nullable = false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_time_signature", nullable = false)
    private TimeSignature timeSignature;

    @OneToMany(mappedBy = "song")
    private Set<SongPart> songParts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "song")
    private Set<BandSongVersion> bandSongVersions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "song")
    private Set<SongInstrumentalPart> songInstrumentalParts = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "songs")
    private Set<Key> keys = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song song)) return false;
        return Objects.equals(id, song.id) && Objects.equals(imageFileURL, song.imageFileURL) && Objects.equals(audioFileURL, song.audioFileURL) && Objects.equals(mediaURL, song.mediaURL) && Objects.equals(name, song.name) && Objects.equals(bpm, song.bpm) && Objects.equals(songStructure, song.songStructure) && Objects.equals(key, song.key) && Objects.equals(artist, song.artist) && Objects.equals(timeSignature, song.timeSignature) && Objects.equals(songParts, song.songParts) && Objects.equals(bandSongVersions, song.bandSongVersions) && Objects.equals(songInstrumentalParts, song.songInstrumentalParts) && Objects.equals(keys, song.keys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageFileURL, audioFileURL, mediaURL, name, bpm, songStructure, key, artist, timeSignature, songParts, bandSongVersions, songInstrumentalParts, keys);
    }
}
