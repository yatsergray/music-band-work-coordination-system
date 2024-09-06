package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.InvitationDTO;
import ua.yatsergray.backend.domain.dto.band.editable.InvitationEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Invitation;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ParticipationStatusMapper.class})
public interface InvitationMapper {

    InvitationMapper INSTANCE = Mappers.getMapper(InvitationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "band", ignore = true)
    @Mapping(target = "participationStatus", ignore = true)
    Invitation mapToInvitation(InvitationEditableDTO invitationEditableDTO);

    @Mapping(source = "participationStatus", target = "participationStatusDTO")
    InvitationDTO mapToInvitationDTO(Invitation invitation);

    @Mapping(source = "band.id", target = "bandUUID")
    @Mapping(source = "participationStatus.id", target = "participationStatusUUID")
    InvitationEditableDTO mapToInvitationEditableDTO(Invitation invitation);

    List<InvitationDTO> mapAllToInvitationDTOList(List<Invitation> invitations);
}
