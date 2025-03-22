package ua.yatsergray.backend.v2.service;

import org.springframework.data.domain.Page;
import ua.yatsergray.backend.v2.domain.dto.EventSongDTO;
import ua.yatsergray.backend.v2.domain.request.EventSongCreateRequest;
import ua.yatsergray.backend.v2.domain.request.EventSongUpdateRequest;
import ua.yatsergray.backend.v2.exception.*;

import java.util.Optional;
import java.util.UUID;

public interface EventSongService {

    EventSongDTO addEventSong(EventSongCreateRequest eventSongCreateRequest) throws NoSuchEventException, NoSuchSongException, EventSongConflictException, EventSongAlreadyExistsException;

    Optional<EventSongDTO> getEventSongById(UUID eventSongId);

//    List<EventSongDTO> getAllEventSongs();

//    Page<EventSongDTO> getAllEventSongsByPageAndSize(int page, int size);

    Page<EventSongDTO> getAllEventSongsByEventIdAndPageAndSize(UUID eventId, int page, int size);

    EventSongDTO modifyEventSongById(UUID eventSongId, EventSongUpdateRequest eventSongUpdateRequest) throws NoSuchEventSongException, EventSongAlreadyExistsException;

    void removeEventSongById(UUID eventSongId) throws NoSuchEventSongException;
}
