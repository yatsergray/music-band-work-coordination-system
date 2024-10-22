package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.ArtistDTO;
import ua.yatsergray.backend.domain.dto.song.editable.ArtistEditableDTO;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.song.ArtistAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchArtistException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtistService {

    ArtistDTO addArtist(ArtistEditableDTO artistEditableDTO) throws ArtistAlreadyExistsException;

    Optional<ArtistDTO> getArtistById(UUID artistId);

    List<ArtistDTO> getAllArtists();

    ArtistDTO modifyArtistById(UUID artistId, ArtistEditableDTO artistEditableDTO) throws NoSuchArtistException, ArtistAlreadyExistsException;

    void removeArtistById(UUID artistId) throws NoSuchArtistException, ChildEntityExistsException;
}
