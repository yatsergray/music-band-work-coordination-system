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

    @ManyToMany(mappedBy = "keys")
    private Set<Song> songs = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Key key = (Key) o;
        return getId() != null && Objects.equals(getId(), key.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
