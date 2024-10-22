package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_song_version", nullable = false)
//    private BandSongVersion bandSongVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_band_song_version",
            foreignKey = @ForeignKey(
                    name = "fk_event_band_song_version_band_song_version",
                    value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "FOREIGN KEY (id_band_song_version) REFERENCES band_song_versions (id) ON DELETE SET NULL"
            )
    )
    private BandSongVersion bandSongVersion;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        EventBandSongVersion that = (EventBandSongVersion) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
