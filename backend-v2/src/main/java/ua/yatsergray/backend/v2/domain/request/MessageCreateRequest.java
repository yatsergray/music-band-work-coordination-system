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
public class MessageCreateRequest {

    @NotBlank(message = "Text is mandatory")
    private String text;

    @NotNull(message = "Chat id is mandatory")
    private UUID chatId;

    @NotNull(message = "User id is mandatory")
    private UUID userId;
}
