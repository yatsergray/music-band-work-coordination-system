package ua.yatsergray.backend.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import ua.yatsergray.backend.domain.entity.band.*;

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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "image_file_id", unique = true)
    private UUID imageFileId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Message> messages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<EventUser> eventUsers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<BandUserStageRole> bandUserStageRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<BandUserAccessRole> bandUserAccessRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user",  cascade = CascadeType.REMOVE)
    private Set<ChatUserAccessRole> chatUserAccessRoles = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_role")}
    )
    private Set<Role> roles = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
