package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Artist;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.TimeSignature;
import ua.yatsergray.backend.exception.song.*;
import ua.yatsergray.backend.mapper.song.SongMapper;
import ua.yatsergray.backend.repository.song.ArtistRepository;
import ua.yatsergray.backend.repository.song.KeyRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.repository.song.TimeSignatureRepository;
import ua.yatsergray.backend.service.song.SongService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongServiceImpl implements SongService {
    private final SongMapper songMapper;
    private final SongRepository songRepository;
    private final KeyRepository keyRepository;
    private final ArtistRepository artistRepository;
    private final TimeSignatureRepository timeSignatureRepository;

    @Autowired
    public SongServiceImpl(SongMapper songMapper, SongRepository songRepository, KeyRepository keyRepository, ArtistRepository artistRepository, TimeSignatureRepository timeSignatureRepository) {
        this.songMapper = songMapper;
        this.songRepository = songRepository;
        this.keyRepository = keyRepository;
        this.artistRepository = artistRepository;
        this.timeSignatureRepository = timeSignatureRepository;
    }

    @Override
    public SongDTO addSong(SongEditableDTO songEditableDTO) throws NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, SongAlreadyExistsException {
        return songMapper.mapToSongDTO(songRepository.save(configureSong(new Song(), songEditableDTO)));
    }

    @Override
    public Optional<SongDTO> getSongById(UUID songId) {
        return songRepository.findById(songId).map(songMapper::mapToSongDTO);
    }

    @Override
    public List<SongDTO> getAllSongs() {
        return songMapper.mapAllToSongDTOList(songRepository.findAll());
    }

    @Override
    public SongDTO modifySongById(UUID songId, SongEditableDTO songEditableDTO) throws NoSuchSongException, NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, SongAlreadyExistsException {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=%s does not exist", songId)));

        return songMapper.mapToSongDTO(songRepository.save(configureSong(song, songEditableDTO)));
    }

    @Override
    public void removeSongById(UUID songId) throws NoSuchSongException {
        if (!songRepository.existsById(songId)) {
            throw new NoSuchSongException(String.format("Song with id=%s does not exist", songId));
        }

        songRepository.deleteById(songId);
    }

    private Song configureSong(Song song, SongEditableDTO songEditableDTO) throws NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, SongAlreadyExistsException {
        Key key = keyRepository.findById(songEditableDTO.getKeyUUID())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=%s does not exist", songEditableDTO.getKeyUUID())));
        Artist artist = artistRepository.findById(songEditableDTO.getArtistUUID())
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist with id=%s does not exist", songEditableDTO.getArtistUUID())));
        TimeSignature timeSignature = timeSignatureRepository.findById(songEditableDTO.getTimeSignatureUUID())
                .orElseThrow(() -> new NoSuchTimeSignatureException(String.format("Time signature with id=%s does not exist", songEditableDTO.getTimeSignatureUUID())));

        if (Objects.isNull(song.getId())) {
            if (songRepository.existsByArtistIdAndName(songEditableDTO.getArtistUUID(), songEditableDTO.getName())) {
                throw new SongAlreadyExistsException(String.format("Song with artistId=%s and name=%s already exists", songEditableDTO.getArtistUUID(), songEditableDTO.getName()));
            }
        } else {
            if ((!songEditableDTO.getArtistUUID().equals(song.getArtist().getId()) || !songEditableDTO.getName().equals(song.getName())) && songRepository.existsByArtistIdAndName(songEditableDTO.getArtistUUID(), songEditableDTO.getName())) {
                throw new SongAlreadyExistsException(String.format("Song with artistId=%s and name=%s already exists", songEditableDTO.getArtistUUID(), songEditableDTO.getName()));
            }
        }

        song.setMediaURL(songEditableDTO.getMediaURL());
        song.setName(songEditableDTO.getName());
        song.setBpm(songEditableDTO.getBpm());
        song.setKey(key);
        song.setArtist(artist);
        song.setTimeSignature(timeSignature);

        return song;
    }
}
