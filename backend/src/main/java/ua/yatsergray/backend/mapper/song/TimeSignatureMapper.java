package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.TimeSignatureDTO;
import ua.yatsergray.backend.domain.dto.song.editable.TimeSignatureEditableDTO;
import ua.yatsergray.backend.domain.entity.song.TimeSignature;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeSignatureMapper {

    TimeSignatureMapper INSTANCE = Mappers.getMapper(TimeSignatureMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "songs", ignore = true)
    TimeSignature mapToTimeSignature(TimeSignatureEditableDTO timeSignatureEditableDTO);

    TimeSignatureDTO mapToTimeSignatureDTO(TimeSignature timeSignature);

    TimeSignatureEditableDTO mapToTimeSignatureEditableDTO(TimeSignatureDTO timeSignatureDTO);

    List<TimeSignatureDTO> mapAllToTimeSignatureDTOList(List<TimeSignature> timeSignatures);
}
