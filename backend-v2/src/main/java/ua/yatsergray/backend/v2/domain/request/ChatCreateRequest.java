package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatCreateRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Music band id is mandatory")
    private UUID musicBandId;
}
