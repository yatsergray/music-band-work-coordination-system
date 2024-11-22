package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.entity.song.*;
import ua.yatsergray.backend.domain.request.song.SongCreateUpdateRequest;
import ua.yatsergray.backend.domain.request.song.SongKeyCreateRequest;
import ua.yatsergray.backend.exception.song.*;
import ua.yatsergray.backend.mapper.song.SongMapper;
import ua.yatsergray.backend.repository.song.*;
import ua.yatsergray.backend.service.song.SongService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class SongServiceImpl implements SongService {
    private final SongMapper songMapper;
    private final SongRepository songRepository;
    private final KeyRepository keyRepository;
    private final ArtistRepository artistRepository;
    private final TimeSignatureRepository timeSignatureRepository;
    private final SongPartRepository songPartRepository;
    private final SongPartDetailsRepository songPartDetailsRepository;
    private final SongPartKeyChordRepository songPartKeyChordRepository;

    @Autowired
    public SongServiceImpl(SongMapper songMapper, SongRepository songRepository, KeyRepository keyRepository, ArtistRepository artistRepository, TimeSignatureRepository timeSignatureRepository, SongPartRepository songPartRepository, SongPartDetailsRepository songPartDetailsRepository, SongPartKeyChordRepository songPartKeyChordRepository) {
        this.songMapper = songMapper;
        this.songRepository = songRepository;
        this.keyRepository = keyRepository;
        this.artistRepository = artistRepository;
        this.timeSignatureRepository = timeSignatureRepository;
        this.songPartRepository = songPartRepository;
        this.songPartDetailsRepository = songPartDetailsRepository;
        this.songPartKeyChordRepository = songPartKeyChordRepository;
    }

    @Override
    public SongDTO addSong(SongCreateUpdateRequest songCreateUpdateRequest) throws NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, SongAlreadyExistsException {
        Key key = keyRepository.findById(songCreateUpdateRequest.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", songCreateUpdateRequest.getKeyId())));
        Artist artist = artistRepository.findById(songCreateUpdateRequest.getArtistId())
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist with id=\"%s\" does not exist", songCreateUpdateRequest.getArtistId())));
        TimeSignature timeSignature = timeSignatureRepository.findById(songCreateUpdateRequest.getTimeSignatureId())
                .orElseThrow(() -> new NoSuchTimeSignatureException(String.format("Time signature with id=\"%s\" does not exist", songCreateUpdateRequest.getTimeSignatureId())));

        if (songRepository.existsByArtistIdAndName(songCreateUpdateRequest.getArtistId(), songCreateUpdateRequest.getName())) {
            throw new SongAlreadyExistsException(String.format("Song with artistId=\"%s\" and name=\"%s\" already exists", songCreateUpdateRequest.getArtistId(), songCreateUpdateRequest.getName()));
        }

        Song song = Song.builder()
                .mediaURL(songCreateUpdateRequest.getMediaURL())
                .name(songCreateUpdateRequest.getName())
                .bpm(songCreateUpdateRequest.getBpm())
                .key(key)
                .artist(artist)
                .timeSignature(timeSignature)
                .build();

        return songMapper.mapToSongDTO(songRepository.save(song));
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
    public SongDTO modifySongById(UUID songId, SongCreateUpdateRequest songCreateUpdateRequest) throws NoSuchSongException, NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, SongAlreadyExistsException {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", songId)));
        Key key = keyRepository.findById(songCreateUpdateRequest.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", songCreateUpdateRequest.getKeyId())));
        Artist artist = artistRepository.findById(songCreateUpdateRequest.getArtistId())
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist with id=\"%s\" does not exist", songCreateUpdateRequest.getArtistId())));
        TimeSignature timeSignature = timeSignatureRepository.findById(songCreateUpdateRequest.getTimeSignatureId())
                .orElseThrow(() -> new NoSuchTimeSignatureException(String.format("Time signature with id=\"%s\" does not exist", songCreateUpdateRequest.getTimeSignatureId())));

        if ((!songCreateUpdateRequest.getArtistId().equals(song.getArtist().getId()) || !songCreateUpdateRequest.getName().equals(song.getName())) && songRepository.existsByArtistIdAndName(songCreateUpdateRequest.getArtistId(), songCreateUpdateRequest.getName())) {
            throw new SongAlreadyExistsException(String.format("Song with artistId=\"%s\" and name=\"%s\" already exists", songCreateUpdateRequest.getArtistId(), songCreateUpdateRequest.getName()));
        }

        song.setMediaURL(songCreateUpdateRequest.getMediaURL());
        song.setName(songCreateUpdateRequest.getName());
        song.setBpm(songCreateUpdateRequest.getBpm());
        song.setKey(key);
        song.setArtist(artist);
        song.setTimeSignature(timeSignature);

        return songMapper.mapToSongDTO(songRepository.save(song));
    }

    @Override
    public void removeSongById(UUID songId) throws NoSuchSongException {
        if (!songRepository.existsById(songId)) {
            throw new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", songId));
        }

        List<SongPartKeyChord> availableToDeleteSongPartKeyChords = songPartKeyChordRepository.findAvailableToDeleteBySongId(songId);
        List<SongPart> availableToDeleteSongParts = songPartRepository.findAvailableToDeleteBySongId(songId);

        songPartDetailsRepository.deleteBySongId(songId);
        songPartKeyChordRepository.deleteAll(availableToDeleteSongPartKeyChords);
        songPartRepository.deleteAll(availableToDeleteSongParts);
        songRepository.deleteById(songId);
    }

    @Override
    public SongDTO addSongKey(UUID songId, SongKeyCreateRequest songKeyCreateRequest) throws NoSuchSongException, NoSuchKeyException, SongKeyConflictException {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", songId)));
        Key key = keyRepository.findById(songKeyCreateRequest.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", songKeyCreateRequest.getKeyId())));

        if (song.getKey().equals(key) || song.getKeys().contains(key)) {
            throw new SongKeyConflictException(String.format("Song with id=\"%s\" already has key with id=\"%s\"", songId, songKeyCreateRequest.getKeyId()));
        }

        song.getKeys().add(key);

        return songMapper.mapToSongDTO(songRepository.save(song));
    }

    @Override
    public void removeSongKey(UUID songId, UUID keyId) throws NoSuchSongException, NoSuchKeyException, SongKeyConflictException {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", songId)));
        Key key = keyRepository.findById(keyId)
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", keyId)));

        if (!song.getKey().equals(key) && !song.getKeys().contains(key)) {
            throw new SongKeyConflictException(String.format("Song with id=\"%s\" does not have key with id=\"%s\"", songId, key));
        }

        song.getKeys().remove(key);

        songRepository.save(song);
    }
}
