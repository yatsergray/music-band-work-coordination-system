package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.EventSongDTO;
import ua.yatsergray.backend.v2.domain.request.EventSongCreateRequest;
import ua.yatsergray.backend.v2.domain.request.EventSongUpdateRequest;
import ua.yatsergray.backend.v2.exception.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventSongService {

    EventSongDTO addEventSong(EventSongCreateRequest eventSongCreateRequest) throws NoSuchEventException, NoSuchSongException, EventSongConflictException, EventSongAlreadyExistsException;

    Optional<EventSongDTO> getEventSongById(UUID eventSongId);

    List<EventSongDTO> getAllEventSongs();

    EventSongDTO modifyEventSongById(UUID eventSongId, EventSongUpdateRequest eventSongUpdateRequest) throws NoSuchEventSongException, EventSongAlreadyExistsException;

    void removeEventSongById(UUID eventSongId) throws NoSuchEventSongException;
}
