package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongPartDTO;
import ua.yatsergray.backend.domain.request.song.SongPartCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongPartUpdateRequest;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.SongPartAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongPartService {

    SongPartDTO addSongPart(SongPartCreateRequest songPartCreateRequest) throws NoSuchSongException, NoSuchSongPartCategoryException, SongPartAlreadyExistsException;

    Optional<SongPartDTO> getSongPartById(UUID songPartId);

    List<SongPartDTO> getAllSongParts();

    SongPartDTO modifySongPartById(UUID songPartId, SongPartUpdateRequest songPartUpdateRequest) throws NoSuchSongPartException, NoSuchSongException, NoSuchSongPartCategoryException, SongPartAlreadyExistsException;

    void removeSongPartById(UUID songPartId) throws NoSuchSongPartException;
}
