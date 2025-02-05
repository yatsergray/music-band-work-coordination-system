package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.ArtistDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.song.Artist;
import ua.yatsergray.backend.domain.request.song.ArtistCreateRequest;
import ua.yatsergray.backend.domain.request.song.ArtistUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.song.ArtistAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchArtistException;
import ua.yatsergray.backend.mapper.song.ArtistMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.song.ArtistRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.service.song.ArtistService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistMapper artistMapper;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final BandRepository bandRepository;

    @Autowired
    public ArtistServiceImpl(ArtistMapper artistMapper, ArtistRepository artistRepository, SongRepository songRepository, BandRepository bandRepository) {
        this.artistMapper = artistMapper;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.bandRepository = bandRepository;
    }

    @Override
    public ArtistDTO addArtist(ArtistCreateRequest artistCreateRequest) throws ArtistAlreadyExistsException, NoSuchBandException {
        Band band = bandRepository.findById(artistCreateRequest.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", artistCreateRequest.getBandId())));

        if (artistRepository.existsByBandIdAndName(artistCreateRequest.getBandId(), artistCreateRequest.getName())) {
            throw new ArtistAlreadyExistsException(String.format("Artist with bandId=\"%s\" and name=\"%s\" already exists", artistCreateRequest.getBandId(), artistCreateRequest.getName()));
        }

        Artist artist = Artist.builder()
                .name(artistCreateRequest.getName())
                .band(band)
                .build();

        return artistMapper.mapToArtistDTO(artistRepository.save(artist));
    }

    @Override
    public Optional<ArtistDTO> getArtistById(UUID artistId) {
        return artistRepository.findById(artistId).map(artistMapper::mapToArtistDTO);
    }

    @Override
    public List<ArtistDTO> getAllArtists() {
        return artistMapper.mapAllToArtistDTOList(artistRepository.findAll());
    }

    @Override
    public ArtistDTO modifyArtistById(UUID artistId, ArtistUpdateRequest artistUpdateRequest) throws NoSuchArtistException, ArtistAlreadyExistsException {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist with id=\"%s\" does not exist", artistId)));

        if (!artistUpdateRequest.getName().equals(artist.getName()) && artistRepository.existsByBandIdAndName(artist.getBand().getId(), artistUpdateRequest.getName())) {
            throw new ArtistAlreadyExistsException(String.format("Artist with bandId=\"%s\" and name=\"%s\" already exists", artist.getBand().getId(), artistUpdateRequest.getName()));
        }

        artist.setName(artistUpdateRequest.getName());

        return artistMapper.mapToArtistDTO(artistRepository.save(artist));
    }

    @Override
    public void removeArtistById(UUID artistId) throws NoSuchArtistException, ChildEntityExistsException {
        if (!artistRepository.existsById(artistId)) {
            throw new NoSuchArtistException(String.format("Artist with id=\"%s\" does not exist", artistId));
        }

        checkIfArtistHasChildEntity(artistId);

        artistRepository.deleteById(artistId);
    }

    private void checkIfArtistHasChildEntity(UUID artistId) throws ChildEntityExistsException {
        long artistChildEntityAmount = songRepository.countByArtistId(artistId);

        if (artistChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d Song(s) depend(s) on the Artist with id=%s", artistChildEntityAmount, artistId));
        }
    }
}
