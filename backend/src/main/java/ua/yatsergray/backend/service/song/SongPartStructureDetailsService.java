package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongPartStructureDetailsDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartStructureDetailsEditableDTO;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartStructureDetailsException;
import ua.yatsergray.backend.exception.song.NoSuchSongStructureException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongPartStructureDetailsService {

    SongPartStructureDetailsDTO addSongPartStructureDetails(SongPartStructureDetailsEditableDTO songPartStructureDetailsEditableDTO) throws NoSuchSongPartException, NoSuchSongStructureException;

    Optional<SongPartStructureDetailsDTO> getSongPartStructureDetailsById(UUID id);

    List<SongPartStructureDetailsDTO> getAllSongPartStructureDetails();

    SongPartStructureDetailsDTO modifySongPartStructureDetailsById(UUID id, SongPartStructureDetailsEditableDTO songPartStructureDetailsEditableDTO) throws NoSuchSongPartStructureDetailsException, NoSuchSongPartException, NoSuchSongStructureException;

    void removeSongPartStructureDetailsById(UUID id) throws NoSuchSongPartStructureDetailsException;
}
