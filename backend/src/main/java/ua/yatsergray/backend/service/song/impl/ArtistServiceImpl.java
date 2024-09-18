package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.ArtistDTO;
import ua.yatsergray.backend.domain.dto.song.editable.ArtistEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Artist;
import ua.yatsergray.backend.exception.song.ArtistAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchArtistException;
import ua.yatsergray.backend.mapper.song.ArtistMapper;
import ua.yatsergray.backend.repository.song.ArtistRepository;
import ua.yatsergray.backend.service.song.ArtistService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistMapper artistMapper;
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistMapper artistMapper, ArtistRepository artistRepository) {
        this.artistMapper = artistMapper;
        this.artistRepository = artistRepository;
    }

    @Override
    public ArtistDTO addArtist(ArtistEditableDTO artistEditableDTO) throws ArtistAlreadyExistsException {
        return artistMapper.mapToArtistDTO(corfigureArtist(new Artist(), artistEditableDTO));
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
    public ArtistDTO modifyArtistById(UUID artistId, ArtistEditableDTO artistEditableDTO) throws NoSuchArtistException, ArtistAlreadyExistsException {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist with id=%s does not exist", artistId)));

        return artistMapper.mapToArtistDTO(corfigureArtist(artist, artistEditableDTO));
    }

    @Override
    public void removeArtistById(UUID artistId) throws NoSuchArtistException {
        if (!artistRepository.existsById(artistId)) {
            throw new NoSuchArtistException(String.format("Artist with id=%s does not exist", artistId));
        }

        artistRepository.deleteById(artistId);
    }

    private Artist corfigureArtist(Artist artist, ArtistEditableDTO artistEditableDTO) throws ArtistAlreadyExistsException {
        if (Objects.isNull(artist.getId())) {
            if (artistRepository.existsByName(artistEditableDTO.getName())) {
                throw new ArtistAlreadyExistsException(String.format("Artist with name=%s already exists", artistEditableDTO.getName()));
            }
        } else {
            if (!artistEditableDTO.getName().equals(artist.getName()) && artistRepository.existsByName(artistEditableDTO.getName())) {
                throw new ArtistAlreadyExistsException(String.format("Artist with name=%s already exists", artistEditableDTO.getName()));
            }
        }

        artist.setName(artistEditableDTO.getName());

        return artist;
    }
}
