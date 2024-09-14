package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.ArtistDTO;
import ua.yatsergray.backend.domain.dto.song.editable.ArtistEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Artist;
import ua.yatsergray.backend.exception.song.NoSuchArtistException;
import ua.yatsergray.backend.mapper.song.ArtistMapper;
import ua.yatsergray.backend.repository.song.ArtistRepository;
import ua.yatsergray.backend.service.song.ArtistService;

import java.util.List;
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
    public ArtistDTO addArtist(ArtistEditableDTO artistEditableDTO) {
        Artist artist = Artist.builder()
                .name(artistEditableDTO.getName())
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
    public ArtistDTO modifyArtistById(UUID artistId, ArtistEditableDTO artistEditableDTO) throws NoSuchArtistException {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist with id=%s does not exist", artistId)));

        artist.setName(artistEditableDTO.getName());

        return artistMapper.mapToArtistDTO(artistRepository.save(artist));
    }

    @Override
    public void removeArtistById(UUID artistId) throws NoSuchArtistException {
        if (!artistRepository.existsById(artistId)) {
            throw new NoSuchArtistException(String.format("Artist with id=%s does not exist", artistId));
        }

        artistRepository.deleteById(artistId);
    }
}
