package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message message)) return false;
        return Objects.equals(id, message.id) && Objects.equals(text, message.text) && Objects.equals(date, message.date) && Objects.equals(time, message.time) && Objects.equals(edited, message.edited) && Objects.equals(chat, message.chat) && Objects.equals(user, message.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, date, time, edited, chat, user);
    }
}
