package ua.yatsergray.backend.domain.request.song;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartCategoryUpdateRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
