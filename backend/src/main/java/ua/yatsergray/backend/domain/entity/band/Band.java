package ua.yatsergray.backend.domain.entity.band;

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
@Table(name = "bands")
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "image_file_id", unique = true)
    private UUID imageFileId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "band", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Chat> chats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Event> events = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Room> rooms = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Invitation> invitations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<BandSongVersion> bandSongVersions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<BandUserStageRole> bandUserStageRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<BandUserAccessRole> bandUserAccessRoles = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Band band = (Band) o;
        return getId() != null && Objects.equals(getId(), band.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
