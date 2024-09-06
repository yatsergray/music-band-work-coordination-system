package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.EventBandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.EventBandSongVersionEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchBandSongVersionException;
import ua.yatsergray.backend.exception.band.NoSuchEventBandSongVersionException;
import ua.yatsergray.backend.exception.band.NoSuchEventException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventBandSongVersionService {

    EventBandSongVersionDTO addEventBandSongVersion(EventBandSongVersionEditableDTO eventBandSongVersionEditableDTO) throws NoSuchEventException, NoSuchBandSongVersionException;

    Optional<EventBandSongVersionDTO> getEventBandSongVersionById(UUID id);

    List<EventBandSongVersionDTO> getAllEventBandSongVersions();

    EventBandSongVersionDTO modifyEventBandSongVersionById(UUID id, EventBandSongVersionEditableDTO eventBandSongVersionEditableDTO) throws NoSuchEventBandSongVersionException, NoSuchEventException, NoSuchBandSongVersionException;

    void removeEventBandSongVersionById(UUID id) throws NoSuchEventBandSongVersionException;
}
