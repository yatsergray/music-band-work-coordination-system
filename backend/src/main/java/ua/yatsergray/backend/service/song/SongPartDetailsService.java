package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongPartDetailsDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartDetailsEditableDTO;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartDetailsException;
import ua.yatsergray.backend.exception.song.SongPartDetailsAlreadyExistsException;
import ua.yatsergray.backend.exception.song.SongPartDetailsConflictException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongPartDetailsService {

    SongPartDetailsDTO addSongPartDetails(SongPartDetailsEditableDTO songPartDetailsEditableDTO) throws NoSuchSongPartException, SongPartDetailsConflictException, SongPartDetailsAlreadyExistsException;

    Optional<SongPartDetailsDTO> getSongPartDetailsById(UUID songPartDetailsId);

    List<SongPartDetailsDTO> getAllSongPartDetails();

    SongPartDetailsDTO modifySongPartDetailsById(UUID songPartCategoryId, SongPartDetailsEditableDTO songPartDetailsEditableDTO) throws NoSuchSongPartDetailsException, NoSuchSongPartException, SongPartDetailsConflictException, SongPartDetailsAlreadyExistsException;

    void removeSongPartDetailsById(UUID songPartCategoryId) throws NoSuchSongPartDetailsException;
}
