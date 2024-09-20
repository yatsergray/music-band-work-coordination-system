package ua.yatsergray.backend.domain.dto.band.editable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageEditableDTO {

    @NotBlank(message = "Text is mandatory")
    private String text;

    @NotNull(message = "Date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @NotNull(message = "Time is mandatory")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    @NotNull(message = "Chat id is mandatory")
    private UUID chatId;

    @NotNull(message = "User id is mandatory")
    private UUID userId;
}
