package ua.yatsergray.backend.v2.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
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
@Table(name = "music_bands")
public class MusicBand {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @DateTimeFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "musicBand", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Invitation> invitations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "musicBand", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<MusicBandUserAccessRole> musicBandUserAccessRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "musicBand", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<MusicBandUserStageRole> musicBandUserStageRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "musicBand", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Chat> chats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "musicBand", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Song> songs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "musicBand", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Event> events = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        MusicBand musicBand = (MusicBand) o;

        return getId() != null && Objects.equals(getId(), musicBand.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
