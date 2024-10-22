package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongPartDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartEditableDTO;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.SongPartAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongPartService {

    SongPartDTO addSongPart(SongPartEditableDTO songPartEditableDTO) throws NoSuchSongException, NoSuchSongPartCategoryException, SongPartAlreadyExistsException;

    Optional<SongPartDTO> getSongPartById(UUID songPartId);

    List<SongPartDTO> getAllSongParts();

    SongPartDTO modifySongPartById(UUID songPartId, SongPartEditableDTO songPartEditableDTO) throws NoSuchSongPartException, NoSuchSongException, NoSuchSongPartCategoryException, SongPartAlreadyExistsException;

    void removeSongPartById(UUID songPartId) throws NoSuchSongPartException;
}
