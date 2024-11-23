package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.SongPartDetails;

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

    @Column(name = "audio_file_id", unique = true)
    private UUID audioFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_key", nullable = false)
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_band", nullable = false)
    private Band band;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_song", nullable = false)
//    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_song",
            foreignKey = @ForeignKey(
                    name = "fk_band_song_version_song",
                    value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "FOREIGN KEY (id_song) REFERENCES songs (id) ON DELETE SET NULL"
            )
    )
    private Song song;

    @OneToMany(mappedBy = "bandSongVersion", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<SongPartDetails> songPartDetailsSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "bandSongVersion")
    @Builder.Default
    private Set<EventBandSongVersion> eventBandSongVersions = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BandSongVersion that = (BandSongVersion) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
