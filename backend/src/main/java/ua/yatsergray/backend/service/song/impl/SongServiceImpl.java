package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Artist;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.TimeSignature;
import ua.yatsergray.backend.exception.song.NoSuchArtistException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchTimeSignatureException;
import ua.yatsergray.backend.mapper.song.SongMapper;
import ua.yatsergray.backend.repository.song.ArtistRepository;
import ua.yatsergray.backend.repository.song.KeyRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.repository.song.TimeSignatureRepository;
import ua.yatsergray.backend.service.song.SongService;

import java.util.List;
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
    public SongDTO addSong(SongEditableDTO songEditableDTO) throws NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException {
        Key key = keyRepository.findById(songEditableDTO.getKeyUUID())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key does not exist with id=%s", songEditableDTO.getKeyUUID())));
        Artist artist = artistRepository.findById(songEditableDTO.getArtistUUID())
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist does not exist with id=%s", songEditableDTO.getArtistUUID())));
        TimeSignature timeSignature = timeSignatureRepository.findById(songEditableDTO.getTimeSignatureUUID())
                .orElseThrow(() -> new NoSuchTimeSignatureException(String.format("Time signature does not exist with id=%s", songEditableDTO.getTimeSignatureUUID())));
//        String imageFileName = songEditableDTO.getImageFileName();
//        String audioFileName = songEditableDTO.getAudioFileName();

        Song song = songMapper.mapToSong(songEditableDTO);

//        song.setImageFileURL(imageFileName);
//        song.setAudioFileURL(audioFileName);
        song.setKey(key);
        song.setArtist(artist);
        song.setTimeSignature(timeSignature);

        return songMapper.mapToSongDTO(songRepository.save(song));
    }

    @Override
    public Optional<SongDTO> getSongById(UUID id) {
        return songRepository.findById(id).map(songMapper::mapToSongDTO);
    }

    @Override
    public List<SongDTO> getAllSongs() {
        return songMapper.mapAllToSongDTOList(songRepository.findAll());
    }

    @Override
    public SongDTO modifySongById(UUID id, SongEditableDTO songEditableDTO) throws NoSuchSongException, NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new NoSuchSongException(String.format("Song does not exist with id=%s", id)));
        Key key = keyRepository.findById(songEditableDTO.getKeyUUID())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key does not exist with id=%s", songEditableDTO.getKeyUUID())));
        Artist artist = artistRepository.findById(songEditableDTO.getArtistUUID())
                .orElseThrow(() -> new NoSuchArtistException(String.format("Artist does not exist with id=%s", songEditableDTO.getArtistUUID())));
        TimeSignature timeSignature = timeSignatureRepository.findById(songEditableDTO.getTimeSignatureUUID())
                .orElseThrow(() -> new NoSuchTimeSignatureException(String.format("Time signature does not exist with id=%s", songEditableDTO.getTimeSignatureUUID())));
//        String imageFileName = songEditableDTO.getImageFileName();
//        String audioFileName = songEditableDTO.getAudioFileName();

//        song.setImageFileURL(imageFileName);
//        song.setAudioFileURL(audioFileName);
        song.setMediaURL(songEditableDTO.getMediaURL());
        song.setName(songEditableDTO.getName());
        song.setBpm(songEditableDTO.getBpm());
        song.setKey(key);
        song.setArtist(artist);
        song.setTimeSignature(timeSignature);

        return songMapper.mapToSongDTO(songRepository.save(song));
    }

    @Override
    public void removeSongById(UUID id) throws NoSuchSongException {
        if (!songRepository.existsById(id)) {
            throw new NoSuchSongException(String.format("Song does not exist with id=%s", id));
        }

        songRepository.deleteById(id);
    }
}
