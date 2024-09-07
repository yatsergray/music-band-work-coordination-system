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

    @Column(name = "audio_file_id", unique = true, nullable = false)
    private UUID audioFileId;

    @Column(name = "video_file_id", unique = true)
    private UUID videoFileId;

    @Column(name = "tab_file_id", unique = true)
    private UUID tabFileId;

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
        return Objects.equals(id, that.id) && Objects.equals(audioFileId, that.audioFileId) && Objects.equals(videoFileId, that.videoFileId) && Objects.equals(tabFileId, that.tabFileId) && Objects.equals(song, that.song) && Objects.equals(stageRole, that.stageRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audioFileId, videoFileId, tabFileId, song, stageRole);
    }
}
