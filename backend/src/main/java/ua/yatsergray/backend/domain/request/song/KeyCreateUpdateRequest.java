package ua.yatsergray.backend.domain.request.song;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class KeyCreateUpdateRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
