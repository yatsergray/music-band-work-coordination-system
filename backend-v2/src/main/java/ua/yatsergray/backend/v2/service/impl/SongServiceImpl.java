package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.SongDTO;
import ua.yatsergray.backend.v2.domain.entity.MusicBand;
import ua.yatsergray.backend.v2.domain.entity.Song;
import ua.yatsergray.backend.v2.domain.request.SongCreateUpdateRequest;
import ua.yatsergray.backend.v2.exception.NoSuchMusicBandException;
import ua.yatsergray.backend.v2.exception.NoSuchSongException;
import ua.yatsergray.backend.v2.mapper.SongMapper;
import ua.yatsergray.backend.v2.repository.MusicBandRepository;
import ua.yatsergray.backend.v2.repository.SongRepository;
import ua.yatsergray.backend.v2.service.SongService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class SongServiceImpl implements SongService {
    private final SongMapper songMapper;
    private final SongRepository songRepository;
    private final MusicBandRepository musicBandRepository;

    @Autowired
    public SongServiceImpl(SongMapper songMapper, SongRepository songRepository, MusicBandRepository musicBandRepository) {
        this.songMapper = songMapper;
        this.songRepository = songRepository;
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public SongDTO addSong(SongCreateUpdateRequest songCreateUpdateRequest) throws NoSuchMusicBandException {
        MusicBand musicBand = musicBandRepository.findById(songCreateUpdateRequest.getBandId())
                .orElseThrow(() -> new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", songCreateUpdateRequest.getBandId())));

        // TODO: Add check if songCategory and added song have the same band
        // TODO: Add check if songMood and added song have the same band

        Song song = Song.builder()
                .name("PASS")
                .musicBand(musicBand)
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
    public SongDTO modifySongById(UUID songId, SongCreateUpdateRequest songCreateUpdateRequest) throws NoSuchSongException {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", songId)));

        // TODO: Add check if songCategory and modified song have the same band
        // TODO: Add check if songMood and modified song have the same band

        song.setName("PASS UPDATED");

        return songMapper.mapToSongDTO(songRepository.save(song));
    }

    @Override
    public void removeSongById(UUID songId) throws NoSuchSongException {
        if (!songRepository.existsById(songId)) {
            throw new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", songId));
        }

        songRepository.deleteById(songId);
    }
}
