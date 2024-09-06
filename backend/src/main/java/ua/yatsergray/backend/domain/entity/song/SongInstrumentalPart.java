package ua.yatsergray.backend.domain.entity.song;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.entity.band.StageRole;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "song_instrumental_parts")
public class SongInstrumentalPart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "audio_file_url", unique = true, nullable = false)
    private String audioFileURL;

    @Column(name = "video_file_url", unique = true)
    private String videoFileURL;

    @Column(name = "tab_file_url", unique = true)
    private String tabFileURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song", nullable = false)
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stage_role", nullable = false)
    private StageRole stageRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongInstrumentalPart that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(audioFileURL, that.audioFileURL) && Objects.equals(videoFileURL, that.videoFileURL) && Objects.equals(tabFileURL, that.tabFileURL) && Objects.equals(song, that.song) && Objects.equals(stageRole, that.stageRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audioFileURL, videoFileURL, tabFileURL, song, stageRole);
    }
}
