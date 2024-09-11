package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongInstrumentalPartDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongInstrumentalPartEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchSongInstrumentalPartException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongInstrumentalPartService {

    SongInstrumentalPartDTO addSongInstrumentalPart(SongInstrumentalPartEditableDTO songInstrumentalPartEditableDTO) throws NoSuchSongException, NoSuchStageRoleException;

    Optional<SongInstrumentalPartDTO> getSongInstrumentalPartById(UUID songInstrumentalPartId);

    List<SongInstrumentalPartDTO> getAllSongInstrumentalParts();

    SongInstrumentalPartDTO modifySongInstrumentalPartById(UUID songInstrumentalPartId, SongInstrumentalPartEditableDTO songInstrumentalPartEditableDTO) throws NoSuchSongInstrumentalPartException, NoSuchSongException, NoSuchStageRoleException;

    void removeSongInstrumentalPartById(UUID songInstrumentalPartId) throws NoSuchSongInstrumentalPartException;
}
