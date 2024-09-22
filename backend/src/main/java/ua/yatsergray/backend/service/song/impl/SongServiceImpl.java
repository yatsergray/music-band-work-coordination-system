package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongEditableDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongKeyEditableDTO;
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

    @Override
    public SongDTO addSongKey(SongKeyEditableDTO songKeyEditableDTO) throws NoSuchSongException, NoSuchKeyException, SongKeyConflictException {
        Song song = songRepository.findById(songKeyEditableDTO.getSongId())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=%s does not exist", songKeyEditableDTO.getSongId())));
        Key key = keyRepository.findById(songKeyEditableDTO.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=%s does not exist", songKeyEditableDTO.getKeyId())));

        if (song.getKeys().contains(key)) {
            throw new SongKeyConflictException(String.format("Song with id=%s already has key with id=%s", songKeyEditableDTO.getSongId(), songKeyEditableDTO.getKeyId()));
        }

        song.getKeys().add(key);

        return songMapper.mapToSongDTO(songRepository.save(song));
    }

    @Override
    public SongDTO removeSongKey(SongKeyEditableDTO  songKeyEditableDTO) throws NoSuchSongException, NoSuchKeyException, SongKeyConflictException {
        Song song = songRepository.findById(songKeyEditableDTO.getSongId())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=%s does not exist", songKeyEditableDTO.getSongId())));
        Key key = keyRepository.findById(songKeyEditableDTO.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=%s does not exist", songKeyEditableDTO.getKeyId())));

        if (!song.getKeys().contains(key)) {
            throw new SongKeyConflictException(String.format("Song with id=%s does not have key with id=%s", songKeyEditableDTO.getSongId(), songKeyEditableDTO.getKeyId()));
        }

        song.getKeys().remove(key);

        return songMapper.mapToSongDTO(songRepository.save(song));
    }

    private Song configureSong(Song song, SongEditableDTO songEditableDTO) throws NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, SongAlreadyExistsException {
        Key key = keyRepository.findById(songEditableDTO.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=%s does not exist", songEditableDTO.getKeyId())));
        Artist artist = artistRepository.findById(songEditableDTO.getArtistId())
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist with id=%s does not exist", songEditableDTO.getArtistId())));
        TimeSignature timeSignature = timeSignatureRepository.findById(songEditableDTO.getTimeSignatureId())
                .orElseThrow(() -> new NoSuchTimeSignatureException(String.format("Time signature with id=%s does not exist", songEditableDTO.getTimeSignatureId())));

        if (Objects.isNull(song.getId())) {
            if (songRepository.existsByArtistIdAndName(songEditableDTO.getArtistId(), songEditableDTO.getName())) {
                throw new SongAlreadyExistsException(String.format("Song with artistId=%s and name=%s already exists", songEditableDTO.getArtistId(), songEditableDTO.getName()));
            }
        } else {
            if ((!songEditableDTO.getArtistId().equals(song.getArtist().getId()) || !songEditableDTO.getName().equals(song.getName())) && songRepository.existsByArtistIdAndName(songEditableDTO.getArtistId(), songEditableDTO.getName())) {
                throw new SongAlreadyExistsException(String.format("Song with artistId=%s and name=%s already exists", songEditableDTO.getArtistId(), songEditableDTO.getName()));
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
