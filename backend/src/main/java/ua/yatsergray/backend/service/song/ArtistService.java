package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.ArtistDTO;
import ua.yatsergray.backend.domain.request.song.ArtistCreateRequest;
import ua.yatsergray.backend.domain.request.song.ArtistUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.song.ArtistAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchArtistException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtistService {

    ArtistDTO addArtist(ArtistCreateRequest artistCreateRequest) throws ArtistAlreadyExistsException, NoSuchBandException;

    Optional<ArtistDTO> getArtistById(UUID artistId);

    List<ArtistDTO> getAllArtists();

    ArtistDTO modifyArtistById(UUID artistId, ArtistUpdateRequest artistUpdateRequest) throws NoSuchArtistException, ArtistAlreadyExistsException;

    void removeArtistById(UUID artistId) throws NoSuchArtistException, ChildEntityExistsException;
}
