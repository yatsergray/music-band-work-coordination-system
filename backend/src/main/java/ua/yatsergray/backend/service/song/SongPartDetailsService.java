package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongPartDetailsDTO;
import ua.yatsergray.backend.domain.request.song.SongPartDetailsCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongPartDetailsUpdateRequest;
import ua.yatsergray.backend.exception.song.NoSuchSongPartDetailsException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.SongPartDetailsAlreadyExistsException;
import ua.yatsergray.backend.exception.song.SongPartDetailsConflictException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongPartDetailsService {

    SongPartDetailsDTO addSongPartDetails(SongPartDetailsCreateRequest songPartDetailsCreateRequest) throws NoSuchSongPartException, SongPartDetailsConflictException, SongPartDetailsAlreadyExistsException;

    Optional<SongPartDetailsDTO> getSongPartDetailsById(UUID songPartDetailsId);

    List<SongPartDetailsDTO> getAllSongPartDetails();

    SongPartDetailsDTO modifySongPartDetailsById(UUID songPartDetailsId, SongPartDetailsUpdateRequest songPartDetailsUpdateRequest) throws NoSuchSongPartDetailsException, SongPartDetailsAlreadyExistsException;

    void removeSongPartDetailsById(UUID songPartDetailsId) throws NoSuchSongPartDetailsException;
}
