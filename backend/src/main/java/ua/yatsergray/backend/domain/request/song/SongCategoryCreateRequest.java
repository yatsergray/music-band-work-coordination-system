package ua.yatsergray.backend.domain.request.song;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongCategoryCreateRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Band id is mandatory")
    private UUID bandId;
}
