package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongMoodDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.song.SongMood;
import ua.yatsergray.backend.domain.request.song.SongMoodCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongMoodUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.song.NoSuchSongMoodException;
import ua.yatsergray.backend.exception.song.SongMoodAlreadyExistsException;
import ua.yatsergray.backend.mapper.song.SongMoodMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.song.SongMoodRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.service.song.SongMoodService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongMoodServiceImpl implements SongMoodService {
    private final SongMoodMapper songMoodMapper;
    private final SongMoodRepository songMoodRepository;
    private final BandRepository bandRepository;
    private final SongRepository songRepository;

    @Autowired
    public SongMoodServiceImpl(SongMoodMapper songMoodMapper, SongMoodRepository songMoodRepository, BandRepository bandRepository, SongRepository songRepository) {
        this.songMoodMapper = songMoodMapper;
        this.songMoodRepository = songMoodRepository;
        this.bandRepository = bandRepository;
        this.songRepository = songRepository;
    }

    @Override
    public SongMoodDTO addSongMood(SongMoodCreateRequest songMoodCreateRequest) throws NoSuchBandException, SongMoodAlreadyExistsException {
        Band band = bandRepository.findById(songMoodCreateRequest.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", songMoodCreateRequest.getBandId())));

        if (songMoodRepository.existsByBandIdAndName(songMoodCreateRequest.getBandId(), songMoodCreateRequest.getName())) {
            throw new SongMoodAlreadyExistsException(String.format("Song mood with bandId=\"%s\" and name=\"%s\" already exists", songMoodCreateRequest.getBandId(), songMoodCreateRequest.getName()));
        }

        SongMood songMood = SongMood.builder()
                .name(songMoodCreateRequest.getName())
                .band(band)
                .build();

        return songMoodMapper.mapToSongMoodDTO(songMoodRepository.save(songMood));
    }

    @Override
    public Optional<SongMoodDTO> getSongMoodById(UUID songMoodId) {
        return songMoodRepository.findById(songMoodId).map(songMoodMapper::mapToSongMoodDTO);
    }

    @Override
    public List<SongMoodDTO> getAllSongMoods() {
        return songMoodMapper.mapAllToSongMoodDTOList(songMoodRepository.findAll());
    }

    @Override
    public SongMoodDTO modifySongMoodById(UUID songMoodId, SongMoodUpdateRequest songMoodUpdateRequest) throws NoSuchSongMoodException {
        SongMood songMood = songMoodRepository.findById(songMoodId)
                .orElseThrow(() -> new NoSuchSongMoodException(String.format("Song mood with id=\"%s\" does not exist", songMoodId)));

        if (!songMoodUpdateRequest.getName().equals(songMood.getName()) && songMoodRepository.existsByBandIdAndName(songMood.getBand().getId(), songMoodUpdateRequest.getName())) {
            throw new NoSuchSongMoodException(String.format("Song mood with bandId=\"%s\" and name=\"%s\" already exists", songMood.getBand().getId(), songMoodUpdateRequest.getName()));
        }

        songMood.setName(songMoodUpdateRequest.getName());

        return songMoodMapper.mapToSongMoodDTO(songMoodRepository.save(songMood));
    }

    @Override
    public void removeSongMoodById(UUID songMoodId) throws NoSuchSongMoodException, ChildEntityExistsException {
        if (!songMoodRepository.existsById(songMoodId)) {
            throw new NoSuchSongMoodException(String.format("Song mood with id=\"%s\" does not exist", songMoodId));
        }

        checkIfSongMoodHasChildEntity(songMoodId);

        songMoodRepository.deleteById(songMoodId);
    }

    private void checkIfSongMoodHasChildEntity(UUID songMoodId) throws ChildEntityExistsException {
        long songMoodChildEntityAmount = songRepository.countBySongMoodId(songMoodId);

        if (songMoodChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Song(s) depend(s) on the Song mood with id=\"%s\"", songMoodChildEntityAmount, songMoodId));
        }
    }
}
