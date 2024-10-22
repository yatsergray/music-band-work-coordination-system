package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import ua.yatsergray.backend.domain.entity.user.User;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "event_users")
public class EventUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_user", nullable = false)
//    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_user",
            foreignKey = @ForeignKey(
                    name = "fk_event_user_user",
                    value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE SET NULL"
            )
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_event", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stage_role", nullable = false)
    private StageRole stageRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_participation_status", nullable = false)
    private ParticipationStatus participationStatus;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        EventUser eventUser = (EventUser) o;
        return getId() != null && Objects.equals(getId(), eventUser.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
