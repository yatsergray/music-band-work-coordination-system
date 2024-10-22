package ua.yatsergray.backend.domain.entity.song;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

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

    @Column(name = "type_number", nullable = false)
    private Integer typeNumber;

    @Column(name = "measures_number")
    private Integer measuresNumber;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_song", nullable = false)
//    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_song",
            foreignKey = @ForeignKey(
                    name = "fk_song_part_song",
                    value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "FOREIGN KEY (id_song) REFERENCES songs (id) ON DELETE SET NULL"
            )
    )
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song_part_category", nullable = false)
    private SongPartCategory songPartCategory;

    @OneToMany(mappedBy = "songPart", cascade = CascadeType.REMOVE)
    // TODO: REMOVE ALL EXCEPT OF THE Chords WHICH FIT TO BandSongVersion Key, Remove , cascade = CascadeType.REMOVE declaration
    private Set<SongPartKeyChord> songPartKeyChords = new LinkedHashSet<>();

    @OneToMany(mappedBy = "songPart")
    // TODO: Remove , cascade = CascadeType.REMOVE declaration
    private Set<SongPartDetails> songPartDetailsSet = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SongPart songPart = (SongPart) o;
        return getId() != null && Objects.equals(getId(), songPart.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
