package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.EventBandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventBandSongVersionEditableDTO;
import ua.yatsergray.backend.exception.band.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventBandSongVersionService {

    EventBandSongVersionDTO addEventBandSongVersion(EventBandSongVersionEditableDTO eventBandSongVersionEditableDTO) throws NoSuchEventException, NoSuchBandSongVersionException, EventBandSongVersionConflictException, EventBandSongVersionAlreadyExistsException;

    Optional<EventBandSongVersionDTO> getEventBandSongVersionById(UUID eventBandSongVersionId);

    List<EventBandSongVersionDTO> getAllEventBandSongVersions();

    EventBandSongVersionDTO modifyEventBandSongVersionById(UUID eventBandSongVersionId, EventBandSongVersionEditableDTO eventBandSongVersionEditableDTO) throws NoSuchEventBandSongVersionException, NoSuchEventException, NoSuchBandSongVersionException, EventBandSongVersionConflictException, EventBandSongVersionAlreadyExistsException;

    void removeEventBandSongVersionById(UUID eventBandSongVersionId) throws NoSuchEventBandSongVersionException;
}
