package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.TimeSignatureDTO;
import ua.yatsergray.backend.domain.request.song.TimeSignatureCreateUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.song.NoSuchTimeSignatureException;
import ua.yatsergray.backend.exception.song.TimeSignatureAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimeSignatureService {

    TimeSignatureDTO addTimeSignature(TimeSignatureCreateUpdateRequest timeSignatureCreateUpdateRequest) throws TimeSignatureAlreadyExistsException;

    Optional<TimeSignatureDTO> getTimeSignatureById(UUID timeSignatureId);

    List<TimeSignatureDTO> getAllTimeSignatures();

    TimeSignatureDTO modifyTimeSignatureById(UUID timeSignatureId, TimeSignatureCreateUpdateRequest timeSignatureCreateUpdateRequest) throws NoSuchTimeSignatureException, TimeSignatureAlreadyExistsException;

    void removeTimeSignatureById(UUID timeSignatureId) throws NoSuchTimeSignatureException, ChildEntityExistsException;
}
