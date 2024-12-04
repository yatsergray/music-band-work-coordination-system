package ua.yatsergray.backend.domain.entity.song;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
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

    @Column(name = "image_file_id", unique = true)
    private UUID imageFileId;

    @Column(name = "audio_file_id", unique = true)
    private UUID audioFileId;

    @Column(name = "media_url", unique = true)
    private String mediaURL;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "bpm", nullable = false)
    private Integer bpm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_key", nullable = false)
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_artist", nullable = false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_time_signature", nullable = false)
    private TimeSignature timeSignature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song_category", nullable = false)
    private SongCategory songCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song_mood", nullable = false)
    private SongMood songMood;

    @OneToMany(mappedBy = "song")
    @Builder.Default
    // TODO: Remove , cascade = CascadeType.REMOVE declaration
    private Set<SongPart> songParts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "song")
    @Builder.Default
    private Set<BandSongVersion> bandSongVersions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<SongPartDetails> songPartDetailsSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<SongInstrumentalPart> songInstrumentalParts = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(
            name = "song_keys",
            joinColumns = {@JoinColumn(name = "id_song")},
            inverseJoinColumns = {@JoinColumn(name = "id_key")}
    )
    @Builder.Default
    private Set<Key> keys = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Song song = (Song) o;
        return getId() != null && Objects.equals(getId(), song.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
