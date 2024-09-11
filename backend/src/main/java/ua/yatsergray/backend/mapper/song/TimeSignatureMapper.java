package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.TimeSignatureDTO;
import ua.yatsergray.backend.domain.entity.song.TimeSignature;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeSignatureMapper {

    TimeSignatureMapper INSTANCE = Mappers.getMapper(TimeSignatureMapper.class);

    TimeSignatureDTO mapToTimeSignatureDTO(TimeSignature timeSignature);

    List<TimeSignatureDTO> mapAllToTimeSignatureDTOList(List<TimeSignature> timeSignatures);
}
