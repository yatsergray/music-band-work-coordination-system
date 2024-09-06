package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.TimeSignatureDTO;
import ua.yatsergray.backend.domain.dto.song.editable.TimeSignatureEditableDTO;
import ua.yatsergray.backend.exception.song.NoSuchTimeSignatureException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimeSignatureService {

    TimeSignatureDTO addTimeSignature(TimeSignatureEditableDTO timeSignatureEditableDTO);

    Optional<TimeSignatureDTO> getTimeSignatureById(UUID id);

    List<TimeSignatureDTO> getAllTimeSignatures();

    TimeSignatureDTO modifyTimeSignatureById(UUID id, TimeSignatureEditableDTO timeSignatureEditableDTO) throws NoSuchTimeSignatureException;

    void removeTimeSignatureById(UUID id) throws NoSuchTimeSignatureException;
}
