package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.song.*;
import ua.yatsergray.backend.domain.request.song.SongCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongKeyCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongUpdateRequest;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.song.*;
import ua.yatsergray.backend.mapper.song.SongMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
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
    private final SongCategoryRepository songCategoryRepository;
    private final SongMoodRepository songMoodRepository;
    private final BandRepository bandRepository;

    @Autowired
    public SongServiceImpl(SongMapper songMapper, SongRepository songRepository, KeyRepository keyRepository, ArtistRepository artistRepository, TimeSignatureRepository timeSignatureRepository, SongPartRepository songPartRepository, SongPartDetailsRepository songPartDetailsRepository, SongPartKeyChordRepository songPartKeyChordRepository, SongCategoryRepository songCategoryRepository, SongMoodRepository songMoodRepository, BandRepository bandRepository) {
        this.songMapper = songMapper;
        this.songRepository = songRepository;
        this.keyRepository = keyRepository;
        this.artistRepository = artistRepository;
        this.timeSignatureRepository = timeSignatureRepository;
        this.songPartRepository = songPartRepository;
        this.songPartDetailsRepository = songPartDetailsRepository;
        this.songPartKeyChordRepository = songPartKeyChordRepository;
        this.songCategoryRepository = songCategoryRepository;
        this.songMoodRepository = songMoodRepository;
        this.bandRepository = bandRepository;
    }

    @Override
    public SongDTO addSong(SongCreateRequest songCreateRequest) throws NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, SongAlreadyExistsException, NoSuchSongCategoryException, NoSuchSongMoodException, NoSuchBandException {
        Key key = keyRepository.findById(songCreateRequest.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", songCreateRequest.getKeyId())));
        Artist artist = artistRepository.findById(songCreateRequest.getArtistId())
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist with id=\"%s\" does not exist", songCreateRequest.getArtistId())));
        TimeSignature timeSignature = timeSignatureRepository.findById(songCreateRequest.getTimeSignatureId())
                .orElseThrow(() -> new NoSuchTimeSignatureException(String.format("Time signature with id=\"%s\" does not exist", songCreateRequest.getTimeSignatureId())));
        SongCategory songCategory = songCategoryRepository.findById(songCreateRequest.getSongCategoryId())
                .orElseThrow(() -> new NoSuchSongCategoryException(String.format("Song category with id=\"%s\" does not exist", songCreateRequest.getSongCategoryId())));
        SongMood songMood = songMoodRepository.findById(songCreateRequest.getSongMoodId())
                .orElseThrow(() -> new NoSuchSongMoodException(String.format("Song mood with id=\"%s\" does not exist", songCreateRequest.getSongMoodId())));
        Band band = bandRepository.findById(songCreateRequest.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", songCreateRequest.getBandId())));

        if (songRepository.existsByArtistIdAndName(songCreateRequest.getArtistId(), songCreateRequest.getName())) {
            throw new SongAlreadyExistsException(String.format("Song with artistId=\"%s\" and name=\"%s\" already exists", songCreateRequest.getArtistId(), songCreateRequest.getName()));
        }

        // TODO: Add check if songCategory and added song have the same band
        // TODO: Add check if songMood and added song have the same band

        Song song = Song.builder()
                .mediaURL(songCreateRequest.getMediaURL())
                .name(songCreateRequest.getName())
                .bpm(songCreateRequest.getBpm())
                .key(key)
                .artist(artist)
                .timeSignature(timeSignature)
                .songCategory(songCategory)
                .songMood(songMood)
                .band(band)
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
    public SongDTO modifySongById(UUID songId, SongUpdateRequest songUpdateRequest) throws NoSuchSongException, NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, NoSuchSongCategoryException, NoSuchSongMoodException, SongAlreadyExistsException {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", songId)));
        Key key = keyRepository.findById(songUpdateRequest.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", songUpdateRequest.getKeyId())));
        Artist artist = artistRepository.findById(songUpdateRequest.getArtistId())
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist with id=\"%s\" does not exist", songUpdateRequest.getArtistId())));
        TimeSignature timeSignature = timeSignatureRepository.findById(songUpdateRequest.getTimeSignatureId())
                .orElseThrow(() -> new NoSuchTimeSignatureException(String.format("Time signature with id=\"%s\" does not exist", songUpdateRequest.getTimeSignatureId())));
        SongCategory songCategory = songCategoryRepository.findById(songUpdateRequest.getSongCategoryId())
                .orElseThrow(() -> new NoSuchSongCategoryException(String.format("Song category with id=\"%s\" does not exist", songUpdateRequest.getSongCategoryId())));
        SongMood songMood = songMoodRepository.findById(songUpdateRequest.getSongMoodId())
                .orElseThrow(() -> new NoSuchSongMoodException(String.format("Song mood with id=\"%s\" does not exist", songUpdateRequest.getSongMoodId())));

        if ((!songUpdateRequest.getArtistId().equals(song.getArtist().getId()) || !songUpdateRequest.getName().equals(song.getName())) && songRepository.existsByArtistIdAndName(songUpdateRequest.getArtistId(), songUpdateRequest.getName())) {
            throw new SongAlreadyExistsException(String.format("Song with artistId=\"%s\" and name=\"%s\" already exists", songUpdateRequest.getArtistId(), songUpdateRequest.getName()));
        }

        // TODO: Add check if songCategory and modified song have the same band
        // TODO: Add check if songMood and modified song have the same band

        song.setMediaURL(songUpdateRequest.getMediaURL());
        song.setName(songUpdateRequest.getName());
        song.setBpm(songUpdateRequest.getBpm());
        song.setKey(key);
        song.setArtist(artist);
        song.setTimeSignature(timeSignature);
        song.setSongCategory(songCategory);
        song.setSongMood(songMood);

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
