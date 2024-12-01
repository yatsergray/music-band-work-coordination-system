package ua.yatsergray.backend.domain.request.band;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventStatusUpdateRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
