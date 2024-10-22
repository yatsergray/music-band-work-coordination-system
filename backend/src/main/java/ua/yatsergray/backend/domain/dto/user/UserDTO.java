package ua.yatsergray.backend.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private UUID id;
    private UUID imageFileId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @JsonProperty("roles")
    private List<RoleDTO> roleDTOList = new ArrayList<>();
}
