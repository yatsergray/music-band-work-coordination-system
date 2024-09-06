package ua.yatsergray.backend.domain.entity.song;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "time_signatures")
public class TimeSignature {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "beats", nullable = false)
    private Integer beats;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @OneToMany(mappedBy = "timeSignature")
    private Set<Song> songs = new LinkedHashSet<>();
}
