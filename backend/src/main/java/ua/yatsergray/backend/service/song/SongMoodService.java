package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongMoodDTO;
import ua.yatsergray.backend.domain.request.song.SongMoodCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongMoodUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.song.NoSuchSongMoodException;
import ua.yatsergray.backend.exception.song.SongMoodAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongMoodService {

    SongMoodDTO addSongMood(SongMoodCreateRequest songMoodCreateRequest) throws NoSuchBandException, SongMoodAlreadyExistsException;

    Optional<SongMoodDTO> getSongMoodById(UUID songMoodId);

    List<SongMoodDTO> getAllSongMoods();

    SongMoodDTO modifySongMoodById(UUID songMoodId, SongMoodUpdateRequest songMoodUpdateRequest) throws NoSuchSongMoodException;

    void removeSongMoodById(UUID songMoodId) throws NoSuchSongMoodException, ChildEntityExistsException;
}
