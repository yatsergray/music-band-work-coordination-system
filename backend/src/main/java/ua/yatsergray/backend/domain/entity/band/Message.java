package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;
import ua.yatsergray.backend.domain.entity.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "text", nullable = false)
    private String text;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "edited")
    private Boolean edited;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat", nullable = false)
    private Chat chat;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_user", nullable = false)
//    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_user",
            foreignKey = @ForeignKey(
                    name = "fk_message_user",
                    value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE SET NULL"
            )
    )
    private User user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Message message = (Message) o;
        return getId() != null && Objects.equals(getId(), message.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
