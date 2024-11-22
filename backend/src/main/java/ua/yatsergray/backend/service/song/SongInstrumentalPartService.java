package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongInstrumentalPartDTO;
import ua.yatsergray.backend.domain.request.song.SongInstrumentalPartCreateRequest;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchSongInstrumentalPartException;
import ua.yatsergray.backend.exception.song.SongInstrumentalPartAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongInstrumentalPartService {

    SongInstrumentalPartDTO addSongInstrumentalPart(SongInstrumentalPartCreateRequest songInstrumentalPartCreateRequest) throws NoSuchSongException, NoSuchStageRoleException, SongInstrumentalPartAlreadyExistsException;

    Optional<SongInstrumentalPartDTO> getSongInstrumentalPartById(UUID songInstrumentalPartId);

    List<SongInstrumentalPartDTO> getAllSongInstrumentalParts();

    void removeSongInstrumentalPartById(UUID songInstrumentalPartId) throws NoSuchSongInstrumentalPartException;
}
