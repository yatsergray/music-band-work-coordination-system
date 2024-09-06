package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_band", nullable = false)
    private Band band;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_event_category", nullable = false)
    private EventCategory eventCategory;

    @OneToMany(mappedBy = "event")
    private Set<EventUser> eventUsers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "event")
    private Set<EventBandSongVersion> eventBandSongVersions = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event event)) return false;
        return Objects.equals(id, event.id) && Objects.equals(date, event.date) && Objects.equals(startTime, event.startTime) && Objects.equals(endTime, event.endTime) && Objects.equals(band, event.band) && Objects.equals(eventCategory, event.eventCategory) && Objects.equals(eventUsers, event.eventUsers) && Objects.equals(eventBandSongVersions, event.eventBandSongVersions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, startTime, endTime, band, eventCategory, eventUsers, eventBandSongVersions);
    }
}
