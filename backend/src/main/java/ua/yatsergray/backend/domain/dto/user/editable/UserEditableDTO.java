package ua.yatsergray.backend.domain.dto.user.editable;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEditableDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
