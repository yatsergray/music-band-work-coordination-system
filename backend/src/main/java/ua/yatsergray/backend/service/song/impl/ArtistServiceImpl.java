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
//        String imageFileName = artistEditableDTO.getImageFileName();

        Artist artist = artistMapper.mapToArtist(artistEditableDTO);

//        artist.setImageFileURL(imageFileName);

        return artistMapper.mapToArtistDTO(artistRepository.save(artist));
    }

    @Override
    public Optional<ArtistDTO> getArtistById(UUID id) {
        return artistRepository.findById(id).map(artistMapper::mapToArtistDTO);
    }

    @Override
    public List<ArtistDTO> getAllArtists() {
        return artistMapper.mapAllToArtistDTOList(artistRepository.findAll());
    }

    @Override
    public ArtistDTO modifyArtistById(UUID id, ArtistEditableDTO artistEditableDTO) throws NoSuchArtistException {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist does not exist with id=%s", id)));

//        String imageFileName = artistEditableDTO.getImageFileName();

//        artist.setImageFileURL(imageFileName);
        artist.setName(artistEditableDTO.getName());

        return artistMapper.mapToArtistDTO(artistRepository.save(artist));
    }

    @Override
    public void removeArtistById(UUID id) throws NoSuchArtistException {
        if (!artistRepository.existsById(id)) {
            throw new NoSuchArtistException(String.format("Artist does not exist with id=%s", id));
        }

        artistRepository.deleteById(id);
    }
}
